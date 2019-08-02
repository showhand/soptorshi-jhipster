import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';
import { ProductPriceService } from 'app/entities/product-price';
import { IProductPrice } from 'app/shared/model/product-price.model';
import { RequisitionDetailsService, RequisitionDetailsUpdateComponent } from 'app/entities/requisition-details';

@Component({
    selector: 'jhi-requisition-details-extended-update',
    templateUrl: './requisition-details-extended-update.component.html'
})
export class RequisitionDetailsExtendedUpdateComponent extends RequisitionDetailsUpdateComponent {
    productPrice: IProductPrice;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected requisitionDetailsService: RequisitionDetailsService,
        protected requisitionService: RequisitionService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute,
        protected productPriceService: ProductPriceService
    ) {
        super(jhiAlertService, requisitionDetailsService, requisitionService, productService, activatedRoute, productPriceService);
    }

    productSelected(productId: number) {
        this.productPriceService
            .query({
                'productId.equals': productId
            })
            .subscribe((res: HttpResponse<IProduct[]>) => {
                this.productPrice = res.body[res.body.length - 1];
                let amount = this.productPrice.price;
            });
    }

    calculateQuantity() {
        if (this.requisitionDetails.unitPrice && this.requisitionDetails.unit) {
            const quantity = this.requisitionDetails.unitPrice * this.requisitionDetails.unit;
            this.requisitionDetails.quantity = quantity;
        }
    }
}
