import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { AcademicInformation, IAcademicInformation } from 'app/shared/model/academic-information.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { AcademicInformationService } from './academic-information.service';
import { IEmployee } from 'app/shared/model/employee.model';

@Component({
    selector: 'jhi-academic-information',
    templateUrl: './academic-information.component.html'
})
export class AcademicInformationComponent implements OnInit, OnDestroy {
    @Input()
    employee: IEmployee = <IEmployee>{};
    @Output()
    closeEmployeeManagement: EventEmitter<any> = new EventEmitter();
    currentAccount: any;
    academicInformations: IAcademicInformation[];
    academicInformation: IAcademicInformation;
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    showAcademicInformationManagementSection: boolean;
    showAddOrUpdateSection: boolean;

    constructor(
        protected academicInformationService: AcademicInformationService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        /*this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams === undefined ? 1 : data.pagingParams;
            this.previousPage = data.pagingParams === undefined ? 1 : data.pagingParams;
            this.reverse = data.pagingParams === undefined ? true : data.pagingParams.ascending;
            this.predicate = data.pagingParams === undefined ? 'id' : data.pagingParams.predicate;
        });*/
        this.page = 1;
        this.previousPage = 1;
        this.reverse = true;
        this.predicate = 'id';
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        /*if (this.currentSearch) {
            this.academicInformationService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IAcademicInformation[]>) => this.paginateAcademicInformations(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }*/
        this.academicInformationService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                'employeeId.equals': this.employee.id
            })
            .subscribe(
                (res: HttpResponse<IAcademicInformation[]>) => this.paginateAcademicInformations(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/academic-information'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.router.navigate([
            '/academic-information',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.router.navigate([
            '/academic-information',
            {
                search: this.currentSearch,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        if (this.employee.id === undefined) {
            this.jhiAlertService.error('Please fill up personal information');
        } else {
            this.academicInformationService.employee = this.employee;
            this.showAcademicInformationManagementSection = true;
            this.loadAll();
            this.accountService.identity().then(account => {
                this.currentAccount = account;
            });
            this.registerChangeInAcademicInformations();
        }
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAcademicInformation) {
        return item.id;
    }

    registerChangeInAcademicInformations() {
        this.eventSubscriber = this.eventManager.subscribe('academicInformationListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    close() {
        this.closeEmployeeManagement.emit();
    }

    showAcademicInformation() {
        this.loadAll();
        this.showAcademicInformationManagementSection = true;
        this.academicInformation = new AcademicInformation();
    }

    addAcademicInformation() {
        this.academicInformation = new AcademicInformation();
        this.showAcademicInformationManagementSection = false;
        this.showAddOrUpdateSection = true;
    }

    edit(academicInformation: IAcademicInformation) {
        this.academicInformation = academicInformation;
        this.showAcademicInformationManagementSection = false;
        this.showAddOrUpdateSection = true;
    }

    editAcademicInformation() {
        this.showAcademicInformationManagementSection = false;
        this.showAddOrUpdateSection = true;
    }

    viewAcademicInformation(academicInformation: IAcademicInformation) {
        this.academicInformation = academicInformation;
        this.showAddOrUpdateSection = false;
        this.showAcademicInformationManagementSection = false;
    }

    protected paginateAcademicInformations(data: IAcademicInformation[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.academicInformations = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
