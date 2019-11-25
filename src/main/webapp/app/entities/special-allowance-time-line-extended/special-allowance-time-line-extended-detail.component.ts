import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';
import { SpecialAllowanceTimeLineDetailComponent } from 'app/entities/special-allowance-time-line';

@Component({
    selector: 'jhi-special-allowance-time-line-detail',
    templateUrl: './special-allowance-time-line-extended-detail.component.html'
})
export class SpecialAllowanceTimeLineExtendedDetailComponent extends SpecialAllowanceTimeLineDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
