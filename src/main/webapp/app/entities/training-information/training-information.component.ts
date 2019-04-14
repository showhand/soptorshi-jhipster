import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ITrainingInformation, TrainingInformation } from 'app/shared/model/training-information.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { TrainingInformationService } from './training-information.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { AcademicInformation, IAcademicInformation } from 'app/shared/model/academic-information.model';

@Component({
    selector: 'jhi-training-information',
    templateUrl: './training-information.component.html'
})
export class TrainingInformationComponent implements OnInit, OnDestroy {
    @Input()
    employee: IEmployee;
    @Output()
    closeEmployeeManagement: EventEmitter<any> = new EventEmitter();
    currentAccount: any;
    trainingInformation: ITrainingInformation;
    trainingInformations: ITrainingInformation[];
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
    showTrainingInformationSection: boolean;
    showAddOrUpdateSection: boolean;
    showDeleteDialog: boolean;

    constructor(
        protected trainingInformationService: TrainingInformationService,
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
    }

    loadAll() {
        this.trainingInformationService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                'employeeId.equals': this.employee.id
            })
            .subscribe(
                (res: HttpResponse<ITrainingInformation[]>) => this.paginateTrainingInformations(res.body, res.headers),
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
        this.router.navigate(['/training-information'], {
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
            '/training-information',
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
            '/training-information',
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
            this.showTrainingInformationSection = true;
            this.showDeleteDialog = false;
            this.loadAll();
            this.accountService.identity().then(account => {
                this.currentAccount = account;
            });
            this.registerChangeInTrainingInformations();
        }
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITrainingInformation) {
        return item.id;
    }

    registerChangeInTrainingInformations() {
        this.eventSubscriber = this.eventManager.subscribe('trainingInformationListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateTrainingInformations(data: ITrainingInformation[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.trainingInformations = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    close() {
        this.closeEmployeeManagement.emit();
    }

    showTrainingInformation() {
        this.loadAll();
        this.showTrainingInformationSection = true;
        this.showDeleteDialog = false;
        this.trainingInformation = new TrainingInformation();
    }

    delete(trainingInformation: ITrainingInformation) {
        this.trainingInformation = trainingInformation;
        this.showDeleteDialog = true;
    }

    addTrainingInformation() {
        this.trainingInformation = new TrainingInformation();
        this.trainingInformation.employeeId = this.employee.id;
        this.showTrainingInformationSection = false;
        this.showAddOrUpdateSection = true;
    }

    edit(trainingInformation: ITrainingInformation) {
        this.trainingInformation = trainingInformation;
        this.showTrainingInformationSection = false;
        this.showAddOrUpdateSection = true;
    }

    editTrainingInformation() {
        this.showTrainingInformationSection = false;
        this.showAddOrUpdateSection = true;
    }

    viewTrainingInformation(trainingInformation: ITrainingInformation) {
        this.trainingInformation = trainingInformation;
        this.showAddOrUpdateSection = false;
        this.showTrainingInformationSection = false;
    }
}
