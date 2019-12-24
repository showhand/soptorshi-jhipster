import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';

@Component({
    selector: 'jhi-commercial-product-info-detail',
    templateUrl: './commercial-product-info-detail.component.html'
})
export class CommercialProductInfoDetailComponent implements OnInit {
    commercialProductInfo: ICommercialProductInfo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialProductInfo }) => {
            this.commercialProductInfo = commercialProductInfo;
        });
    }

    previousState() {
        window.history.back();
    }
}
