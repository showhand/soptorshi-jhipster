import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { ProductionExtendedService } from './production-extended.service';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { ProductionUpdateComponent } from 'app/entities/production';
import { DATE_TIME_FORMAT } from 'app/shared';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { IProduct } from 'app/shared/model/product.model';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionDetailsService } from 'app/entities/requisition-details';
import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { RequisitionExtendedService } from 'app/entities/requisition-extended/requisition-extended.service';

@Component({
    selector: 'jhi-production-update-extended',
    templateUrl: './production-update-extended.component.html'
})
export class ProductionUpdateExtendedComponent extends ProductionUpdateComponent {
    requisitionDetails: IRequisitionDetails[];
    masterCopyOfProducts: IProduct[];

    maxVal: number;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productionService: ProductionExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected requisitionService: RequisitionExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected requisitionDetailsService: RequisitionDetailsService
    ) {
        super(jhiAlertService, productionService, productCategoryService, productService, requisitionService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ production }) => {
            this.production = production;
            this.createdOn = this.production.createdOn != null ? this.production.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.production.updatedOn != null ? this.production.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe(
                (res: IProduct[]) => {
                    this.products = res;
                    this.masterCopyOfProducts = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.requisitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisition[]>) => response.body)
            )
            .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    getRequisitionDetails() {
        this.requisitionDetailsService
            .query({
                'requisitionId.equals': this.production.requisitionsId
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisitionDetails[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisitionDetails[]>) => response.body)
            )
            .subscribe(
                (res: IRequisitionDetails[]) => {
                    this.requisitionDetails = res;
                    this.setProducts();
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    setProducts() {
        this.products = [];
        for (let i = 0; i < this.masterCopyOfProducts.length; i++) {
            for (let j = 0; j < this.requisitionDetails.length; j++) {
                if (this.masterCopyOfProducts[i].id === this.requisitionDetails[j].productId) {
                    this.products.push(this.masterCopyOfProducts[i]);
                }
            }
        }
        if (this.products) {
            this.production.productsId = this.products[0].id;
            this.getQuantityOfSelectedProduct();
        }
    }

    getUnitOfSelectedProduct() {
        /*console.log("************1213********");
        for(let i = 0; i < this.requisitionDetails.length; i++) {
            console.log(this.production.productsId + "===" + this.requisitionDetails[i].productId);
            if(this.production.productsId === this.requisitionDetails[i].productId) {
                console.log("********************");
                console.log(this.requisitionDetails[i].uom.toString());
            }
        }*/
    }

    getQuantityOfSelectedProduct() {
        this.getUnitOfSelectedProduct();
        for (let i = 0; i < this.requisitionDetails.length; i++) {
            if (this.production.productsId === this.requisitionDetails[i].productId) {
                this.production.quantity = this.requisitionDetails[i].unit;
                this.maxVal = this.production.quantity;
            }
        }
    }

    validateQuantity() {
        if (this.production.quantity < 0) {
            this.isSaving = true;
            this.onError('Quantity can not be zero');
        } else if (this.production.quantity > this.maxVal && this.production.weightStep === 'RAW') {
            this.isSaving = true;
            this.onError('Quantity can not be bigger than provided in requisition');
        } else {
            this.isSaving = false;
        }
    }

    validateByProduct() {
        if (this.production.byProductQuantity < 0) {
            this.isSaving = true;
            this.onError('By product quantity can not be zero');
        } else if (this.production.byProductQuantity > this.maxVal && this.production.weightStep === 'RAW') {
            this.isSaving = true;
            this.onError('By product quantity can not be bigger than provided in requisition');
        } else if (this.production.byProductQuantity + this.production.quantity === this.maxVal && this.production.weightStep === 'RAW') {
            this.isSaving = true;
            this.onError('By product quantity and quantity summation should be equal to total value');
        } else {
            this.isSaving = false;
        }
    }

    setProductCategory() {
        let selectedRequisition: IRequisition;
        selectedRequisition = this.requisitions.find(requisition => requisition.id == this.production.requisitionsId);
        this.production.productCategoriesId = this.productcategories.find(
            productCategory => productCategory.id == selectedRequisition.productCategoryId
        ).id;
        this.getRequisitionDetails();
    }
}
