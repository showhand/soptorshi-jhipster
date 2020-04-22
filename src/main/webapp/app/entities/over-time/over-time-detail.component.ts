import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOverTime } from 'app/shared/model/over-time.model';

@Component({
    selector: 'jhi-over-time-detail',
    templateUrl: './over-time-detail.component.html'
})
export class OverTimeDetailComponent implements OnInit {
    overTime: IOverTime;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ overTime }) => {
            this.overTime = overTime;
        });
    }

    previousState() {
        window.history.back();
    }
}
