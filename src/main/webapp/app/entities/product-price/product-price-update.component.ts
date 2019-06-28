import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProductPrice } from 'app/shared/model/product-price.model';
import { ProductPriceService } from './product-price.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product';

@Component({
    selector: 'jhi-product-price-update',
    templateUrl: './product-price-update.component.html'
})
export class ProductPriceUpdateComponent implements OnInit {
    productPrice: IProductPrice;
    isSaving: boolean;

    products: IProduct[];
    priceDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productPriceService: ProductPriceService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productPrice }) => {
            this.productPrice = productPrice;
        });
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productPrice.id !== undefined) {
            this.subscribeToSaveResponse(this.productPriceService.update(this.productPrice));
        } else {
            this.subscribeToSaveResponse(this.productPriceService.create(this.productPrice));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductPrice>>) {
        result.subscribe((res: HttpResponse<IProductPrice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductById(index: number, item: IProduct) {
        return item.id;
    }
}
