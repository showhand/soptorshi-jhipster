import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { ConversionFactorDetailComponent } from 'app/entities/conversion-factor';

@Component({
    selector: 'jhi-conversion-factor-detail',
    templateUrl: './conversion-factor-extended-detail.component.html'
})
export class ConversionFactorExtendedDetailComponent extends ConversionFactorDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
