import { Component } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IAttendance } from 'app/shared/model/attendance.model';
import { AccountService } from 'app/core';

import { DATE_FORMAT } from 'app/shared';
import { AttendanceExtendedService } from './attendance-extended.service';
import * as moment from 'moment';
import { Moment } from 'moment';
import { AttendanceComponent } from 'app/entities/attendance';

@Component({
    selector: 'jhi-attendance-extended',
    templateUrl: './attendance-extended.component.html'
})
export class AttendanceExtendedComponent extends AttendanceComponent {
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
    distinctAttendanceDate: Moment[];

    constructor(
        protected attendanceServiceExtended: AttendanceExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(attendanceServiceExtended, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);

        this.attendanceServiceExtended
            .getDistinctAttendanceDate()
            .subscribe(
                (res: HttpResponse<Moment[]>) => this.addDistinctAttendances(res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadAll() {
        if (this.currentSearch) {
            this.attendanceServiceExtended
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
        } else {
            this.attendanceServiceExtended
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'attendanceDate.equals': moment(new Date()).format(DATE_FORMAT)
                })
                .subscribe(
                    (res: HttpResponse<IAttendance[]>) => this.paginateAttendances(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
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
        this.predicate = 'id';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }

    protected addDistinctAttendances(data: Moment[]) {
        this.distinctAttendanceDate = data;
    }
}
