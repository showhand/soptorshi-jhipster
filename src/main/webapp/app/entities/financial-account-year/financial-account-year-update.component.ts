import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from './financial-account-year.service';

@Component({
    selector: 'jhi-financial-account-year-update',
    templateUrl: './financial-account-year-update.component.html'
})
export class FinancialAccountYearUpdateComponent implements OnInit {
    financialAccountYear: IFinancialAccountYear;
    isSaving: boolean;
    startDateDp: any;
    endDateDp: any;
    previousStartDateDp: any;
    previousEndDateDp: any;

    constructor(protected financialAccountYearService: FinancialAccountYearService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ financialAccountYear }) => {
            this.financialAccountYear = financialAccountYear;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.financialAccountYear.id !== undefined) {
            this.subscribeToSaveResponse(this.financialAccountYearService.update(this.financialAccountYear));
        } else {
            this.subscribeToSaveResponse(this.financialAccountYearService.create(this.financialAccountYear));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFinancialAccountYear>>) {
        result.subscribe(
            (res: HttpResponse<IFinancialAccountYear>) => this.onSaveSuccess(),
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
}
