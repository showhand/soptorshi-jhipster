import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { DepreciationCalculationService } from './depreciation-calculation.service';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';

@Component({
    selector: 'jhi-depreciation-calculation-update',
    templateUrl: './depreciation-calculation-update.component.html'
})
export class DepreciationCalculationUpdateComponent implements OnInit {
    depreciationCalculation: IDepreciationCalculation;
    isSaving: boolean;

    financialaccountyears: IFinancialAccountYear[];
    createdOn: string;
    modifiedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected depreciationCalculationService: DepreciationCalculationService,
        protected financialAccountYearService: FinancialAccountYearService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ depreciationCalculation }) => {
            this.depreciationCalculation = depreciationCalculation;
            this.createdOn =
                this.depreciationCalculation.createdOn != null ? this.depreciationCalculation.createdOn.format(DATE_TIME_FORMAT) : null;
            this.modifiedOn =
                this.depreciationCalculation.modifiedOn != null ? this.depreciationCalculation.modifiedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.financialAccountYearService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFinancialAccountYear[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFinancialAccountYear[]>) => response.body)
            )
            .subscribe(
                (res: IFinancialAccountYear[]) => (this.financialaccountyears = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.depreciationCalculation.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.depreciationCalculation.modifiedOn = this.modifiedOn != null ? moment(this.modifiedOn, DATE_TIME_FORMAT) : null;
        if (this.depreciationCalculation.id !== undefined) {
            this.subscribeToSaveResponse(this.depreciationCalculationService.update(this.depreciationCalculation));
        } else {
            this.subscribeToSaveResponse(this.depreciationCalculationService.create(this.depreciationCalculation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepreciationCalculation>>) {
        result.subscribe(
            (res: HttpResponse<IDepreciationCalculation>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFinancialAccountYearById(index: number, item: IFinancialAccountYear) {
        return item.id;
    }
}
