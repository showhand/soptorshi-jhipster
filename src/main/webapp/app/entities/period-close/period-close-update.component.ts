import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPeriodClose } from 'app/shared/model/period-close.model';
import { PeriodCloseService } from './period-close.service';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';

@Component({
    selector: 'jhi-period-close-update',
    templateUrl: './period-close-update.component.html'
})
export class PeriodCloseUpdateComponent implements OnInit {
    periodClose: IPeriodClose;
    isSaving: boolean;

    financialaccountyears: IFinancialAccountYear[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected periodCloseService: PeriodCloseService,
        protected financialAccountYearService: FinancialAccountYearService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ periodClose }) => {
            this.periodClose = periodClose;
            console.log(this.periodClose);
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
        if (this.periodClose.id !== undefined) {
            this.subscribeToSaveResponse(this.periodCloseService.update(this.periodClose));
        } else {
            this.subscribeToSaveResponse(this.periodCloseService.create(this.periodClose));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodClose>>) {
        result.subscribe((res: HttpResponse<IPeriodClose>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
