import { Component, OnDestroy, OnInit } from '@angular/core';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { Account, AccountService } from 'app/core';
import { Subscription } from 'rxjs';
import { LeaveApplicationService } from 'app/entities/leave-application/leave-application.service';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { ITEMS_PER_PAGE } from 'app/shared';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Employee, IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { ManagerService } from 'app/entities/manager';
import { IManager } from 'app/shared/model/manager.model';

@Component({
    selector: 'jhi-others-leave-application-history',
    templateUrl: './others-leave-application-history.component.html',
    styles: []
})
export class OthersLeaveApplicationHistoryComponent implements OnInit, OnDestroy {
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
    currentEmployee: IEmployee[];

    constructor(
        protected leaveApplicationService: LeaveApplicationService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeService,
        protected managerService: ManagerService
    ) {
        this.leaveApplications = [];
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
    }

    loadAll() {
        this.employeeService
            .query({
                'employeeId.equals': this.currentSearch
            })
            .subscribe(
                (res: HttpResponse<IEmployee[]>) => {
                    this.managerService
                        .query({
                            'parentEmployeeId.equals': res.body[0].id
                        })
                        .subscribe(
                            (res: HttpResponse<IManager[]>) => {
                                if (res.body[0].employeeId == this.currentEmployee[0].id) {
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
                                                (res: HttpResponse<ILeaveApplication[]>) =>
                                                    this.paginateLeaveApplications(res.body, res.headers),
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
                                            (res: HttpResponse<ILeaveApplication[]>) =>
                                                this.paginateLeaveApplications(res.body, res.headers),
                                            (res: HttpErrorResponse) => this.onError(res.message)
                                        );
                                }
                            },
                            (res: HttpErrorResponse) => this.onError(res.message)
                        );
                },
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
            this.employeeService
                .query({
                    'employeeId.equals': this.currentAccount.login
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => {
                        this.currentEmployee = res.body;
                        this.loadAll();
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
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
        this.leaveApplications = [];
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
