import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ExperienceInformation, IExperienceInformation } from 'app/shared/model/experience-information.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { ExperienceInformationService } from './experience-information.service';
import { IEmployee } from 'app/shared/model/employee.model';

@Component({
    selector: 'jhi-experience-information',
    templateUrl: './experience-information.component.html'
})
export class ExperienceInformationComponent implements OnInit, OnDestroy {
    @Input()
    employee: IEmployee;
    @Output()
    closeEmployeeManagement: EventEmitter<any> = new EventEmitter();
    currentAccount: any;
    experienceInformation: IExperienceInformation;
    experienceInformations: IExperienceInformation[];
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
    showExperienceInformationSection: boolean;
    showAddOrUpdateSection: boolean;
    showDeleteDialog: boolean;

    constructor(
        protected experienceInformationService: ExperienceInformationService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
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
        this.experienceInformationService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                'employeeId.equals': this.employee.id
            })
            .subscribe(
                (res: HttpResponse<IExperienceInformation[]>) => this.paginateExperienceInformations(res.body, res.headers),
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
        this.router.navigate(['/experience-information'], {
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
            '/experience-information',
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
            '/experience-information',
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
            this.jhiAlertService.error('Please fill up employee personal information');
        } else {
            this.showExperienceInformationSection = true;
            this.showDeleteDialog = false;
            this.loadAll();
            this.accountService.identity().then(account => {
                this.currentAccount = account;
            });
            this.registerChangeInExperienceInformations();
        }
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IExperienceInformation) {
        return item.id;
    }

    registerChangeInExperienceInformations() {
        this.eventSubscriber = this.eventManager.subscribe('experienceInformationListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateExperienceInformations(data: IExperienceInformation[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.experienceInformations = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    close() {
        this.closeEmployeeManagement.emit();
    }

    showExperienceInformation() {
        this.loadAll();
        this.showExperienceInformationSection = true;
        this.showDeleteDialog = false;
        this.experienceInformation = new ExperienceInformation();
    }
    delete(experienceInformation: ExperienceInformation) {
        this.experienceInformation = this.experienceInformation;
        this.showDeleteDialog = true;
    }

    addExperienceInformation() {
        this.experienceInformation = new ExperienceInformation();
        this.experienceInformation.employeeId = this.employee.id;
        this.showExperienceInformationSection = false;
        this.showAddOrUpdateSection = true;
    }

    edit(experienceInformation: IExperienceInformation) {
        this.experienceInformation = experienceInformation;
        this.showExperienceInformationSection = false;
        this.showAddOrUpdateSection = true;
    }

    editExperienceInformation() {
        this.showExperienceInformationSection = false;
        this.showAddOrUpdateSection = true;
    }

    viewExperienceInformation(experienceInformation: IExperienceInformation) {
        this.experienceInformation = experienceInformation;
        this.showAddOrUpdateSection = false;
        this.showExperienceInformationSection = false;
    }
}
