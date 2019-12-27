import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';
import { CommercialProductInfoDetailComponent } from 'app/entities/commercial-product-info';

@Component({
    selector: 'jhi-commercial-product-info-detail-extended',
    templateUrl: './commercial-product-info-detail-extended.component.html'
})
export class CommercialProductInfoDetailExtendedComponent extends CommercialProductInfoDetailComponent {
    commercialProductInfo: ICommercialProductInfo;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
