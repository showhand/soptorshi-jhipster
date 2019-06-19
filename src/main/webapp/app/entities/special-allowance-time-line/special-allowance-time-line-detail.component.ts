import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';

@Component({
    selector: 'jhi-special-allowance-time-line-detail',
    templateUrl: './special-allowance-time-line-detail.component.html'
})
export class SpecialAllowanceTimeLineDetailComponent implements OnInit {
    specialAllowanceTimeLine: ISpecialAllowanceTimeLine;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ specialAllowanceTimeLine }) => {
            this.specialAllowanceTimeLine = specialAllowanceTimeLine;
        });
    }

    previousState() {
        window.history.back();
    }
}
