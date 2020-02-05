import { Component } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { AccountService } from 'app/core';
import { LeaveApplicationExtendedService } from './leave-application-extended.service';
import { LeaveApplicationComponent } from 'app/entities/leave-application';

@Component({
    selector: 'jhi-leave-application-extended',
    templateUrl: './leave-application-extended.component.html'
})
export class LeaveApplicationExtendedComponent extends LeaveApplicationComponent {
    constructor(
        protected leaveApplicationService: LeaveApplicationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(leaveApplicationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        if (this.currentSearch) {
            this.leaveApplicationService
                .search({
                    query: this.currentSearch,
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'employeeId.equals': this.currentAccount.login
                })
                .subscribe(
                    (res: HttpResponse<ILeaveApplication[]>) => this.paginateLeaveApplications(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.leaveApplicationService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort(),
                'employeeId.equals': this.currentAccount.login
            })
            .subscribe(
                (res: HttpResponse<ILeaveApplication[]>) => this.paginateLeaveApplications(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.leaveApplications = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }
}
