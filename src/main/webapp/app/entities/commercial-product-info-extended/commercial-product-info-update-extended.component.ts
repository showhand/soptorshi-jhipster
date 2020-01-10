import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { CommercialProductInfoExtendedService } from './commercial-product-info-extended.service';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { CommercialProductInfoUpdateComponent } from 'app/entities/commercial-product-info';
import { CommercialBudgetExtendedService } from 'app/entities/commercial-budget-extended';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';

@Component({
    selector: 'jhi-commercial-product-info-update-extended',
    templateUrl: './commercial-product-info-update-extended.component.html'
})
export class CommercialProductInfoUpdateExtendedComponent extends CommercialProductInfoUpdateComponent implements OnInit {
    transportationCost: number;
    priceWithTransportationCost: number;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialProductInfoService: CommercialProductInfoExtendedService,
        protected commercialBudgetService: CommercialBudgetExtendedService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            commercialProductInfoService,
            commercialBudgetService,
            productCategoryService,
            productService,
            activatedRoute
        );
    }

    getTransportationCost() {
        if (this.commercialProductInfo.commercialBudgetId) {
            this.commercialBudgetService
                .query({
                    'id.equals': this.commercialProductInfo.commercialBudgetId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ICommercialBudget[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ICommercialBudget[]>) => response.body)
                )
                .subscribe(
                    (res: ICommercialBudget[]) => this.assignTransportationCost(res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    assignTransportationCost(data: ICommercialBudget[]) {
        this.transportationCost = data[0].totalTransportationCost;
        this.priceWithTransportationCost = data[0].totalTransportationCost;
    }

    calculateTotalOfferedPrice() {
        if (
            this.commercialProductInfo.offeredQuantity &&
            this.commercialProductInfo.offeredUnit &&
            this.commercialProductInfo.offeredUnitPrice
        ) {
            this.commercialProductInfo.offeredTotalPrice =
                this.commercialProductInfo.offeredQuantity * this.commercialProductInfo.offeredUnitPrice;
        }
    }

    calculateBuyingTotalPrice() {
        let innerPackCost: number = this.commercialProductInfo.ipCost === undefined || null ? 0 : this.commercialProductInfo.ipCost;
        let masterCartoonCost: number = this.commercialProductInfo.mcCost === undefined || null ? 0 : this.commercialProductInfo.mcCost;
        let cylinderCost: number = this.commercialProductInfo.cylCost === undefined || null ? 0 : this.commercialProductInfo.cylCost;
        let buyingPrice: number = this.commercialProductInfo.buyingPrice === undefined || null ? 0 : this.commercialProductInfo.buyingPrice;
        let transportationCost: number = this.priceWithTransportationCost === undefined || null ? 0 : this.transportationCost;

        this.commercialProductInfo.buyingTotalPrice = innerPackCost + masterCartoonCost + cylinderCost + buyingPrice;
        this.priceWithTransportationCost = innerPackCost + masterCartoonCost + cylinderCost + buyingPrice + transportationCost;
    }

    calculateBuyingPrice() {
        if (
            this.commercialProductInfo.buyingQuantity &&
            this.commercialProductInfo.buyingUnit &&
            this.commercialProductInfo.buyingUnitPrice
        ) {
            this.commercialProductInfo.buyingPrice = this.commercialProductInfo.buyingQuantity * this.commercialProductInfo.buyingUnitPrice;
            this.calculateBuyingTotalPrice();
        }
    }
}
