import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductPrice } from 'app/shared/model/product-price.model';

@Component({
    selector: 'jhi-product-price-detail',
    templateUrl: './product-price-detail.component.html'
})
export class ProductPriceDetailComponent implements OnInit {
    productPrice: IProductPrice;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productPrice }) => {
            this.productPrice = productPrice;
        });
    }

    previousState() {
        window.history.back();
    }
}
