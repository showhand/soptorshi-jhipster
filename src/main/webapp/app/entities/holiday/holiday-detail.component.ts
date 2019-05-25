import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHoliday } from 'app/shared/model/holiday.model';

@Component({
    selector: 'jhi-holiday-detail',
    templateUrl: './holiday-detail.component.html'
})
export class HolidayDetailComponent implements OnInit {
    holiday: IHoliday;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holiday }) => {
            this.holiday = holiday;
        });
    }

    previousState() {
        window.history.back();
    }
}
