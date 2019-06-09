import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AllowanceManagement, IAllowanceManagement } from 'app/shared/model/allowance-management.model';
import { AccountService } from 'app/core';
import { AllowanceManagementService } from './allowance-management.service';
import { IOffice, Office } from 'app/shared/model/office.model';
import { Designation, IDesignation } from 'app/shared/model/designation.model';
import { OfficeService } from 'app/entities/office';
import { DesignationService } from 'app/entities/designation';
import { detectChanges } from '@angular/core/src/render3';
import { DesignationWiseAllowanceComponent } from 'app/entities/designation-wise-allowance';

@Component({
    selector: 'jhi-allowance-management',
    templateUrl: './allowance-management.component.html'
})
export class AllowanceManagementComponent implements OnInit, OnDestroy {
    @ViewChild(DesignationWiseAllowanceComponent) designationWiseAllowanceComponent: DesignationWiseAllowanceComponent;

    allowanceManagements: IAllowanceManagement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    offices: IOffice[];
    designations: IDesignation[];
    showInformation: boolean;

    constructor(
        public allowanceManagementService: AllowanceManagementService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected officeService: OfficeService,
        protected designationService: DesignationService
    ) {}

    fetch() {
        this.showInformation = true;
        this.designationWiseAllowanceComponent.ngOnInit();
    }

    loadAll() {
        if (this.allowanceManagementService.allowanceManagement === undefined) {
            this.allowanceManagementService.allowanceManagement = new AllowanceManagement();
        }
        this.officeService
            .query({
                page: 0,
                size: 200
            })
            .subscribe(
                (res: HttpResponse<Office[]>) => {
                    this.offices = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.designationService
            .query({
                page: 0,
                size: 200
            })
            .subscribe(
                (res: HttpResponse<Designation[]>) => {
                    this.designations = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.showInformation = false;
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAllowanceManagements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAllowanceManagement) {}

    registerChangeInAllowanceManagements() {
        this.eventSubscriber = this.eventManager.subscribe('allowanceManagementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
