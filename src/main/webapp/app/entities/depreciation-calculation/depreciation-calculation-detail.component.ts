import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';

@Component({
    selector: 'jhi-depreciation-calculation-detail',
    templateUrl: './depreciation-calculation-detail.component.html'
})
export class DepreciationCalculationDetailComponent implements OnInit {
    depreciationCalculation: IDepreciationCalculation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depreciationCalculation }) => {
            this.depreciationCalculation = depreciationCalculation;
        });
    }

    previousState() {
        window.history.back();
    }
}
