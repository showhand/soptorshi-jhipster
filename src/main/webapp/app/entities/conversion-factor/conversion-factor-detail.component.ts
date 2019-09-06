import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConversionFactor } from 'app/shared/model/conversion-factor.model';

@Component({
    selector: 'jhi-conversion-factor-detail',
    templateUrl: './conversion-factor-detail.component.html'
})
export class ConversionFactorDetailComponent implements OnInit {
    conversionFactor: IConversionFactor;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ conversionFactor }) => {
            this.conversionFactor = conversionFactor;
        });
    }

    previousState() {
        window.history.back();
    }
}
