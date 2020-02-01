import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWeekend } from 'app/shared/model/weekend.model';

@Component({
    selector: 'jhi-weekend-detail',
    templateUrl: './weekend-detail.component.html'
})
export class WeekendDetailComponent implements OnInit {
    weekend: IWeekend;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ weekend }) => {
            this.weekend = weekend;
        });
    }

    previousState() {
        window.history.back();
    }
}
