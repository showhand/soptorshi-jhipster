import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';
import { AccountService } from 'app/core';
import { FineAdvanceLoanManagementService } from './fine-advance-loan-management.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

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

    constructor(
        protected fineAdvanceLoanManagementService: FineAdvanceLoanManagementService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeService
    ) {}

    loadAll() {}

    search(query) {}

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.showFinancialSection = true;
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFineAdvanceLoanManagements();
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
}
