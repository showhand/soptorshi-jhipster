import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { HolidayExtendedService } from './holiday-extended.service';
import { HolidayTypeService } from 'app/entities/holiday-type';
import { HolidayUpdateComponent } from 'app/entities/holiday';

@Component({
    selector: 'jhi-holiday-update-extended',
    templateUrl: './holiday-update-extended.component.html'
})
export class HolidayUpdateExtendedComponent extends HolidayUpdateComponent {
    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected holidayService: HolidayExtendedService,
        protected holidayTypeService: HolidayTypeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(dataUtils, jhiAlertService, holidayService, holidayTypeService, activatedRoute);
    }

    calculateDifference() {
        this.holiday.numberOfDays =
            this.holiday.toDate && this.holiday.fromDate ? this.holiday.toDate.diff(this.holiday.fromDate, 'days') + 1 : 0;
    }
}
