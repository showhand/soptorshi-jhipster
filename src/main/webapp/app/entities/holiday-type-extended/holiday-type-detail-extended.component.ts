import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeDetailComponent } from 'app/entities/holiday-type';

@Component({
    selector: 'jhi-holiday-type-detail-extended',
    templateUrl: './holiday-type-detail-extended.component.html'
})
export class HolidayTypeDetailExtendedComponent extends HolidayTypeDetailComponent implements OnInit {
    holidayType: IHolidayType;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holidayType }) => {
            this.holidayType = holidayType;
        });
    }

    previousState() {
        window.history.back();
    }
}
