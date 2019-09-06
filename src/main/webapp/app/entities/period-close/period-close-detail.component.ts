import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodClose } from 'app/shared/model/period-close.model';

@Component({
    selector: 'jhi-period-close-detail',
    templateUrl: './period-close-detail.component.html'
})
export class PeriodCloseDetailComponent implements OnInit {
    periodClose: IPeriodClose;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ periodClose }) => {
            this.periodClose = periodClose;
        });
    }

    previousState() {
        window.history.back();
    }
}
