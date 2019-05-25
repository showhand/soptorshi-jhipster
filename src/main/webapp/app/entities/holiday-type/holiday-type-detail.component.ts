import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHolidayType } from 'app/shared/model/holiday-type.model';

@Component({
    selector: 'jhi-holiday-type-detail',
    templateUrl: './holiday-type-detail.component.html'
})
export class HolidayTypeDetailComponent implements OnInit {
    holidayType: IHolidayType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holidayType }) => {
            this.holidayType = holidayType;
        });
    }

    previousState() {
        window.history.back();
    }
}
