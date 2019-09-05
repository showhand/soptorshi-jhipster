import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IAttendance } from 'app/shared/model/attendance.model';
import { AccountService } from 'app/core';

import { DATE_FORMAT, ITEMS_PER_PAGE } from 'app/shared';
import { AttendanceService } from './attendance.service';
import * as moment from 'moment';
import { Moment } from 'moment';

@Component({
    selector: 'jhi-attendance',
    templateUrl: './attendance.component.html'
})
export class AttendanceComponent implements OnInit, OnDestroy {
    attendances: IAttendance[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;
    distinctAttendanceDate: IAttendance[];
    employeeId: string;

    constructor(
        protected attendanceService: AttendanceService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.attendances = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';

        this.attendanceService
            .getDistinctAttendanceDate()
            .subscribe(
                (res: HttpResponse<IAttendance[]>) => this.addDistinctAttendances(res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadAll() {
        if (this.currentSearch && this.employeeId) {
            this.attendanceService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'attendanceDate.equals': moment(this.currentSearch).format(DATE_FORMAT),
                    'employeeId.equals': this.employeeId
                })
                .subscribe(
                    (res: HttpResponse<IAttendance[]>) => this.paginateAttendances(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.currentSearch) {
            this.attendanceService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'attendanceDate.equals': moment(this.currentSearch).format(DATE_FORMAT)
                })
                .subscribe(
                    (res: HttpResponse<IAttendance[]>) => this.paginateAttendances(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.employeeId) {
            this.attendanceService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'employeeId.equals': this.employeeId
                })
                .subscribe(
                    (res: HttpResponse<IAttendance[]>) => this.paginateAttendances(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.attendanceService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'attendanceDate.equals': moment(new Date())
                        .add(0, 'days')
                        .format(DATE_FORMAT)
                })
                .subscribe(
                    (res: HttpResponse<IAttendance[]>) => this.paginateAttendances(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    reset() {
        this.page = 0;
        this.attendances = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.attendances = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = moment(new Date())
            .add(-1, 'days')
            .toString();
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.attendances = [];
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
        this.registerChangeInAttendances();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAttendance) {
        return item.id;
    }

    registerChangeInAttendances() {
        this.eventSubscriber = this.eventManager.subscribe('attendanceListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateAttendances(data: IAttendance[], headers: HttpHeaders) {
        this.attendances = [];
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            data[i].diff = moment
                .utc(moment(data[i].outTime, 'DD/MM/YYYY HH:mm:ss').diff(moment(data[i].inTime, 'DD/MM/YYYY HH:mm:ss')))
                .format('HH:mm:ss');
            this.attendances.push(data[i]);
        }
    }

    protected addDistinctAttendances(data: IAttendance[]) {
        let flag = 0;
        this.distinctAttendanceDate = [];
        for (let i = 0; i < data.length; i++) {
            for (let j = 0; j < this.distinctAttendanceDate.length; j++) {
                if (this.distinctAttendanceDate[j].attendanceDate.diff(data[i].attendanceDate) === 0) {
                    flag = 1;
                    break;
                } else {
                    flag = 0;
                }
            }
            if (flag === 0) {
                this.distinctAttendanceDate.push(data[i]);
            }
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
