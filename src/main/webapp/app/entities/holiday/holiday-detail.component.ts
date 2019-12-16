import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IHoliday } from 'app/shared/model/holiday.model';

@Component({
    selector: 'jhi-holiday-detail',
    templateUrl: './holiday-detail.component.html'
})
export class HolidayDetailComponent implements OnInit {
    holiday: IHoliday;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holiday }) => {
            this.holiday = holiday;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
