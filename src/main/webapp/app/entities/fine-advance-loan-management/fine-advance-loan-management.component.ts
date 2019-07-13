import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';

import { IFineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';
import { AccountService } from 'app/core';
import { FineAdvanceLoanManagementService } from './fine-advance-loan-management.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
    selector: 'jhi-fine-advance-loan-management',
    templateUrl: './fine-advance-loan-management.component.html'
})
export class FineAdvanceLoanManagementComponent implements OnInit, OnDestroy {
    fineAdvanceLoanManagements: IFineAdvanceLoanManagement[];
    currentAccount: any;
    employees: IEmployee[];
    eventSubscriber: Subscription;
    currentSearch: string;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    showFinancialSection: boolean;
    routeData: any;
    links: any;

    constructor(
        public fineAdvanceLoanManagementService: FineAdvanceLoanManagementService,
        protected jhiAlertService: JhiAlertService,
        protected parseLinks: JhiParseLinks,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected accountService: AccountService,
        protected employeeService: EmployeeService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.employeeService
                .query({
                    'fullName.contains': this.currentSearch,
                    page: this.page - 1,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => {
                        this.paginateEmployees(res.body, res.headers);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }

        this.employeeService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IEmployee[]>) => {
                    this.paginateEmployees(res.body, res.headers);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.router.navigate([
            '/fine-advance-loan-management',
            {
                search: this.currentSearch,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        console.log('EEEEEEEEEmployee');
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFineAdvanceLoanManagements();
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/fine-advance-loan-management'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFineAdvanceLoanManagement) {
        return item.id;
    }

    registerChangeInFineAdvanceLoanManagements() {
        this.eventSubscriber = this.eventManager.subscribe('fineAdvanceLoanManagementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    protected paginateEmployees(data: IEmployee[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.employees = data;
        this.employees.forEach((e: IEmployee) => (e.employeeLongId = e.id));
    }
}
