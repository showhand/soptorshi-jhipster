import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { IRequisition, Requisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { ProductPriceService } from 'app/entities/product-price';
import { IProductPrice } from 'app/shared/model/product-price.model';
import { RequisitionDetailsService, RequisitionDetailsUpdateComponent } from 'app/entities/requisition-details';
import { ProductCategoryService } from 'app/entities/product-category';

@Component({
    selector: 'jhi-requisition-details-extended-update',
    templateUrl: './requisition-details-extended-update.component.html'
})
export class RequisitionDetailsExtendedUpdateComponent extends RequisitionDetailsUpdateComponent implements OnInit {
    productPrice: IProductPrice;
    requisition: IRequisition;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected requisitionDetailsService: RequisitionDetailsService,
        protected productCategoryService: ProductCategoryService,
        protected requisitionService: RequisitionService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute,
        protected productPriceService: ProductPriceService
    ) {
        super(jhiAlertService, requisitionDetailsService, productCategoryService, requisitionService, productService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ requisitionDetails }) => {
            this.requisitionDetails = requisitionDetails;
            this.fetchProducts();
        });
    }

    private fetchProducts() {
        if (this.requisitionDetails.requisitionId) {
            this.requisitionService.find(this.requisitionDetails.requisitionId).subscribe((res: HttpResponse<IRequisition>) => {
                this.requisition = res.body;
                this.productService
                    .query({
                        'productCategoryId.equals': res.body.productCategoryId,
                        size: 2000
                    })
                    .subscribe((response: HttpResponse<IProduct[]>) => {
                        this.products = [];
                        this.products = response.body;
                    });
            });
        } else {
            this.requisitionService
                .query()
                .pipe(
                    filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                    map((response: HttpResponse<IRequisition[]>) => response.body)
                )
                .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
            this.productService
                .query()
                .pipe(
                    filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                    map((response: HttpResponse<IProduct[]>) => response.body)
                )
                .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    productSelected(productId: number) {
        this.productPriceService
            .query({
                'productId.equals': productId
            })
            .subscribe((res: HttpResponse<IProduct[]>) => {
                this.productPrice = res.body[res.body.length - 1];
                const amount = this.productPrice.price;
            });
    }

    calculateQuantity() {
        if (this.requisitionDetails.unitPrice && this.requisitionDetails.unit) {
            const quantity = this.requisitionDetails.unitPrice * this.requisitionDetails.unit;
            this.requisitionDetails.quantity = quantity;
        }
    }
}
