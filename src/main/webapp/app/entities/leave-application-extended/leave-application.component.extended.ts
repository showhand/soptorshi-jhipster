import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { Account, AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { LeaveApplicationServiceExtended } from './leave-application.service.extended';
import { LeaveApplicationComponent } from 'app/entities/leave-application';

@Component({
    selector: 'jhi-leave-application-extended',
    templateUrl: './leave-application.component.extended.html'
})
export class LeaveApplicationComponentExtended extends LeaveApplicationComponent implements OnInit, OnDestroy {
    leaveApplications: ILeaveApplication[];
    currentAccount: Account;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;

    constructor(
        protected leaveApplicationService: LeaveApplicationServiceExtended,
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

    reset() {
        this.page = 0;
        this.leaveApplications = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.leaveApplications = [];
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
        this.leaveApplications = [];
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
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.loadAll();
        });
        this.registerChangeInLeaveApplications();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILeaveApplication) {
        return item.id;
    }

    registerChangeInLeaveApplications() {
        this.eventSubscriber = this.eventManager.subscribe('leaveApplicationListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateLeaveApplications(data: ILeaveApplication[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.leaveApplications.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
