import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayDetailComponent } from 'app/entities/holiday';
import { JhiDataUtils } from 'ng-jhipster';

@Component({
    selector: 'jhi-holiday-detail-extended',
    templateUrl: './holiday-detail-extended.component.html'
})
export class HolidayDetailExtendedComponent extends HolidayDetailComponent implements OnInit {
    holiday: IHoliday;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
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
