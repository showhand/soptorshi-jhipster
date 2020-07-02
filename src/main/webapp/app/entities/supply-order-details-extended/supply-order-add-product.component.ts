import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyOrderDetailsExtendedService } from './supply-order-details-extended.service';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { SupplyOrderExtendedService } from 'app/entities/supply-order-extended';
import { DATE_TIME_FORMAT } from 'app/shared';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyOrder, SupplyOrder } from 'app/shared/model/supply-order.model';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { IProduct } from 'app/shared/model/product.model';
import { SupplyOrderDetailsUpdateExtendedComponent } from 'app/entities/supply-order-details-extended/supply-order-details-update-extended.component';

@Component({
    selector: 'jhi-supply-order-add-product',
    templateUrl: './supply-order-add-product.component.html'
})
export class SupplyOrderAddProductComponent extends SupplyOrderDetailsUpdateExtendedComponent implements OnInit {
    supplyOrder: SupplyOrder;
    supplyOrderId: number;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyOrderDetailsService: SupplyOrderDetailsExtendedService,
        protected supplyOrderService: SupplyOrderExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router
    ) {
        super(
            jhiAlertService,
            supplyOrderDetailsService,
            supplyOrderService,
            productCategoryService,
            productService,
            activatedRoute,
            router
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyOrderDetails }) => {
            this.supplyOrderDetails = supplyOrderDetails;
            this.createdOn = this.supplyOrderDetails.createdOn != null ? this.supplyOrderDetails.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplyOrderDetails.updatedOn != null ? this.supplyOrderDetails.updatedOn.format(DATE_TIME_FORMAT) : null;
        });

        const orderId = this.activatedRoute.snapshot.params['orderId'];
        this.supplyOrderService
            .query({
                'id.equals': orderId
            })
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyOrder[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyOrder[]) => {
                    this.supplyorders = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
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
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        if (this.supplyOrderDetails && this.supplyOrderDetails.supplyOrderId) {
            this.router.navigate(['/supply-order', this.supplyOrderDetails.supplyOrderId, 'edit']);
        } else {
            window.history.back();
        }
    }
}
