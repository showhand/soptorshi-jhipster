import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { FamilyInformation, IFamilyInformation } from 'app/shared/model/family-information.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { FamilyInformationService } from './family-information.service';
import { IEmployee } from 'app/shared/model/employee.model';

@Component({
    selector: 'jhi-family-information',
    templateUrl: './family-information.component.html'
})
export class FamilyInformationComponent implements OnInit, OnDestroy {
    @Input()
    employee: IEmployee;
    @Output()
    closeEmployeeManagement: EventEmitter<any> = new EventEmitter();
    familyInformation: IFamilyInformation;
    currentAccount: any;
    familyInformations: IFamilyInformation[];
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
    showFamilyInformationManagementSection: boolean;
    showAddOrUpdateSection: boolean;
    showDeleteDialog: boolean;

    constructor(
        protected familyInformationService: FamilyInformationService,
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
        this.familyInformationService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort(),
                'employeeId.equals': this.employee.id
            })
            .subscribe(
                (res: HttpResponse<IFamilyInformation[]>) => this.paginateFamilyInformations(res.body, res.headers),
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
        this.router.navigate(['/family-information'], {
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
            '/family-information',
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
            '/family-information',
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
            this.showFamilyInformationManagementSection = true;
            this.loadAll();
            this.accountService.identity().then(account => {
                this.currentAccount = account;
            });
            this.registerChangeInFamilyInformations();
        }
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFamilyInformation) {
        return item.id;
    }

    registerChangeInFamilyInformations() {
        this.eventSubscriber = this.eventManager.subscribe('familyInformationListModification', response => this.loadAll());
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

    showFamilyInformation() {
        this.loadAll();
        this.showFamilyInformationManagementSection = true;
        this.showDeleteDialog = false;
        this.familyInformation = new FamilyInformation();
    }

    delete(familyInformation: IFamilyInformation) {
        this.familyInformation = familyInformation;
        this.showDeleteDialog = true;
    }

    addFamilyInformation() {
        this.familyInformation = new FamilyInformation();
        this.familyInformation.employeeId = this.employee.id;
        this.showFamilyInformationManagementSection = false;
        this.showAddOrUpdateSection = true;
    }

    edit(familyInformation: IFamilyInformation) {
        this.familyInformation = familyInformation;
        this.showFamilyInformationManagementSection = false;
        this.showAddOrUpdateSection = true;
    }

    editFamilyInformation() {
        this.showFamilyInformationManagementSection = false;
        this.showAddOrUpdateSection = true;
    }

    viewFamilyInformation(familyInformation: IFamilyInformation) {
        this.familyInformation = familyInformation;
        this.showAddOrUpdateSection = false;
        this.showFamilyInformationManagementSection = false;
    }

    protected paginateFamilyInformations(data: IFamilyInformation[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.familyInformations = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
