import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommercialBudgetExtendedService } from './commercial-budget-extended.service';
import { CommercialBudgetUpdateComponent } from 'app/entities/commercial-budget';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';
import { DATE_TIME_FORMAT } from 'app/shared';
import { CommercialBudgetStatus } from 'app/shared/model/commercial-budget.model';
import * as moment from 'moment';
import { CommercialProductInfoExtendedService } from 'app/entities/commercial-product-info-extended';

@Component({
    selector: 'jhi-commercial-budget-update-extended',
    templateUrl: './commercial-budget-update-extended.component.html'
})
export class CommercialBudgetUpdateExtendedComponent extends CommercialBudgetUpdateComponent implements OnInit {
    commercialProductInfos: ICommercialProductInfo[];
    approved: boolean = false;
    rejected: boolean = false;

    constructor(
        protected commercialBudgetService: CommercialBudgetExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected commercialProductInfoService: CommercialProductInfoExtendedService
    ) {
        super(commercialBudgetService, activatedRoute, commercialProductInfoService);
        this.commercialProductInfos = [];
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialBudget }) => {
            this.commercialBudget = commercialBudget;
            this.createdOn = this.commercialBudget.createdOn != null ? this.commercialBudget.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.commercialBudget.updatedOn != null ? this.commercialBudget.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.getProductInfo();
    }

    save() {
        this.isSaving = true;
        if (this.approved) {
            this.commercialBudget.budgetStatus = CommercialBudgetStatus.APPROVED;
        }
        if (this.rejected) {
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
}
