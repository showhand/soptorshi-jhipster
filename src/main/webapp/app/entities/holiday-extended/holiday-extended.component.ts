import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

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
export class HolidayExtendedComponent extends HolidayComponent implements OnInit, OnDestroy {
    holidays: IHoliday[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;
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
        protected holidayService: HolidayExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(holidayService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        if (this.currentSearch) {
            this.holidayService
                .search({
                    query: this.currentSearch,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IHoliday[]>) => this.paginateHolidays(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.holidayService
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

    reset() {
        this.page = 0;
        this.holidays = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.holidays = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = '';
        this.loadAll();
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
        this.predicate = '_score';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHolidays();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHoliday) {
        return item.id;
    }

    registerChangeInHolidays() {
        this.eventSubscriber = this.eventManager.subscribe('holidayListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateHolidays(data: IHoliday[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.holidays.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
