import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { DepreciationCalculationDetailComponent } from 'app/entities/depreciation-calculation';

@Component({
    selector: 'jhi-depreciation-calculation-detail',
    templateUrl: './depreciation-calculation-extended-detail.component.html'
})
export class DepreciationCalculationExtendedDetailComponent extends DepreciationCalculationDetailComponent implements OnInit {
    depreciationCalculation: IDepreciationCalculation;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
