import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommercialBudgetExtendedService } from './commercial-budget-extended.service';
import { CommercialBudgetUpdateComponent } from 'app/entities/commercial-budget';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';
import { DATE_TIME_FORMAT } from 'app/shared';
import { CommercialBudgetStatus, CommercialCustomerCategory, ICommercialBudget } from 'app/shared/model/commercial-budget.model';
import * as moment from 'moment';
import { CommercialProductInfoExtendedService } from 'app/entities/commercial-product-info-extended';
import { ICommercialPi } from 'app/shared/model/commercial-pi.model';
import { CommercialPiExtendedService } from 'app/entities/commercial-pi-extended';
import { AccountService } from 'app/core';

@Component({
    selector: 'jhi-commercial-budget-update-extended',
    templateUrl: './commercial-budget-update-extended.component.html'
})
export class CommercialBudgetUpdateExtendedComponent extends CommercialBudgetUpdateComponent implements OnInit {
    commercialProductInfos: ICommercialProductInfo[];
    approved: boolean = false;
    rejected: boolean = false;
    saveAsDraft: boolean = false;
    waitingForApproval: boolean = false;

    showSaveAsDraftBtn: boolean = false;
    showWaitingForApprovalBtn: boolean = false;
    showApproveBtn: boolean = false;
    showRejectBtn: boolean = false;

    isAdmin: boolean = false;

    constructor(
        protected commercialBudgetService: CommercialBudgetExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected commercialProductInfoService: CommercialProductInfoExtendedService,
        protected commercialPiService: CommercialPiExtendedService,
        protected router: Router,
        protected accountService: AccountService
    ) {
        super(commercialBudgetService, activatedRoute);
        this.commercialProductInfos = [];
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialBudget }) => {
            this.commercialBudget = commercialBudget;
            if (!this.commercialBudget.id) {
                this.commercialBudget.budgetStatus = CommercialBudgetStatus.SAVE_AS_DRAFT;
            }
            this.createdOn = this.commercialBudget.createdOn != null ? this.commercialBudget.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.commercialBudget.updatedOn != null ? this.commercialBudget.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.generateNewBudgetNumber();
        this.generateNewPiNumber();
        this.getProductInfo();

        this.isAdmin = this.accountService.hasAnyAuthority(['ROLE_ADMIN', 'ROLE_COMMERCIAL_ADMIN']);
    }

    generateNewBudgetNumber() {
        if (!this.commercialBudget.id) {
            this.commercialBudgetService
                .query()
                .subscribe(
                    (res: HttpResponse<ICommercialBudget[]>) => this.assignBudgetNumber(res.body, res.headers),
                    (res: HttpErrorResponse) => 'Error'
                );
        }
    }

    assignBudgetNumber(data: ICommercialBudget[], headers: HttpHeaders) {
        let today = new Date();
        let year = today.getFullYear();
        let month = today.getMonth() + 1;
        let maxId = data.length + 1;
        this.commercialBudget.budgetNo = 'B-' + year + '-' + ('0' + month).slice(-2) + '-' + this.zeroPadding(maxId, 4);
    }

    generateNewPiNumber() {
        if (!this.commercialBudget.id) {
            this.commercialPiService
                .query()
                .subscribe(
                    (res: HttpResponse<ICommercialPi[]>) => this.assignPiNumber(res.body, res.headers),
                    (res: HttpErrorResponse) => 'Error'
                );
        }
    }

    assignPiNumber(data: ICommercialPi[], headers: HttpHeaders) {
        let today = new Date();
        let year = today.getFullYear();
        let month = today.getMonth() + 1;
        let maxId = data.length + 1;
        this.commercialBudget.proformaNo = 'SOFPL-' + year + '-' + ('0' + month).slice(-2) + '-PI-' + this.zeroPadding(maxId, 4);
    }

    zeroPadding(num, places): string {
        const zero = places - num.toString().length + 1;
        return Array(+(zero > 0 && zero)).join('0') + num;
    }

    calculateTotalTransportationCost() {
        let seaPortPrice: number = this.commercialBudget.seaPortCost === undefined || null ? 0 : this.commercialBudget.seaPortCost;
        let airPortPrice: number = this.commercialBudget.airPortCost === undefined || null ? 0 : this.commercialBudget.airPortCost;
        let landPortPrice: number = this.commercialBudget.landPortCost === undefined || null ? 0 : this.commercialBudget.landPortCost;
        let insurancePrice: number = this.commercialBudget.insurancePrice === undefined || null ? 0 : this.commercialBudget.insurancePrice;

        this.commercialBudget.totalTransportationCost = seaPortPrice + airPortPrice + landPortPrice + insurancePrice;
    }

    save() {
        this.isSaving = true;
        if (this.saveAsDraft) {
            this.commercialBudget.budgetStatus = CommercialBudgetStatus.SAVE_AS_DRAFT;
        } else if (this.waitingForApproval) {
            this.commercialBudget.budgetStatus = CommercialBudgetStatus.WAITING_FOR_APPROVAL;
        } else if (this.approved) {
            this.commercialBudget.budgetStatus = CommercialBudgetStatus.APPROVED;
        } else if (this.rejected) {
            this.commercialBudget.budgetStatus = CommercialBudgetStatus.REJECTED;
        }
        this.commercialBudget.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.commercialBudget.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.commercialBudget.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialBudgetService.update(this.commercialBudget));
        } else {
            this.subscribeToSaveResponse(this.commercialBudgetService.create(this.commercialBudget));
        }
    }

    protected onSaveSuccess() {
        this.isSaving = true;
        this.previousState();
    }

    getProductInfo() {
        this.commercialProductInfoService
            .query({
                'commercialBudgetId.equals': this.commercialBudget.id == undefined ? 0 : this.commercialBudget.id
            })
            .subscribe(
                (res: HttpResponse<ICommercialProductInfo[]>) => this.assignProductInfos(res.body, res.headers),
                (res: HttpErrorResponse) => 'error'
            );
    }

    protected assignProductInfos(data: ICommercialProductInfo[], headers: HttpHeaders) {
        this.commercialProductInfos = [];
        for (let i = 0; i < data.length; i++) {
            this.commercialProductInfos.push(data[i]);
        }
    }

    updateCustomer() {
        if (this.commercialBudget.type === 'LOCAL') {
            this.commercialBudget.customer = CommercialCustomerCategory.ZONE;
        } else if (this.commercialBudget.type === 'FOREIGN') {
            this.commercialBudget.customer = CommercialCustomerCategory.FOREIGN;
        }
    }
}
