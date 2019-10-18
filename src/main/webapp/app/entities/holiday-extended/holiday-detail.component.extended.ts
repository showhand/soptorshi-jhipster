import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayDetailComponent } from 'app/entities/holiday';

@Component({
    selector: 'jhi-holiday-detail-extended',
    templateUrl: './holiday-detail.component.extended.html'
})
export class HolidayDetailComponentExtended extends HolidayDetailComponent implements OnInit {
    holiday: IHoliday;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holiday }) => {
            this.holiday = holiday;
        });
    }

    previousState() {
        window.history.back();
    }
}
