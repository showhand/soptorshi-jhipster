import { Component } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IHoliday } from 'app/shared/model/holiday.model';
import { AccountService } from 'app/core';

import { DATE_FORMAT } from 'app/shared';
import { HolidayExtendedService } from './holiday-extended.service';
import * as moment from 'moment';
import { HolidayComponent } from 'app/entities/holiday';

@Component({
    selector: 'jhi-holiday-extended',
    templateUrl: './holiday-extended.component.html'
})
export class HolidayExtendedComponent extends HolidayComponent {
    year: number = moment().get('year');
    startOfYear = moment()
        .year(this.year)
        .startOf('year')
        .format(DATE_FORMAT);
    endOfYear = moment()
        .year(this.year)
        .endOf('year')
        .format(DATE_FORMAT);

    constructor(
        protected holidayExtendedService: HolidayExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(holidayExtendedService, jhiAlertService, dataUtils, eventManager, parseLinks, activatedRoute, accountService);

        this.predicate = 'fromDate';
    }

    loadAll() {
        if (this.currentSearch) {
            this.holidayExtendedService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'holidayYear.equals': this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IHoliday[]>) => this.paginateHolidays(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.holidayExtendedService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort(),
                'fromDate.greaterOrEqualThan': this.startOfYear,
                'toDate.lessOrEqualThan': this.endOfYear
            })
            .subscribe(
                (res: HttpResponse<IHoliday[]>) => this.paginateHolidays(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.holidays = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'fromDate';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }

    generateReport() {
        if (this.currentSearch) {
            this.holidayExtendedService.generateReportByYear(+this.currentSearch);
        } else {
            this.holidayExtendedService.generateReport();
        }
    }
}
