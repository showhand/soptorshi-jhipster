import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';

@Component({
    selector: 'jhi-designation-wise-allowance-detail',
    templateUrl: './designation-wise-allowance-detail.component.html'
})
export class DesignationWiseAllowanceDetailComponent implements OnInit {
    designationWiseAllowance: IDesignationWiseAllowance;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ designationWiseAllowance }) => {
            this.designationWiseAllowance = designationWiseAllowance;
        });
    }

    previousState() {
        window.history.back();
    }
}
