import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialPi } from 'app/shared/model/commercial-pi.model';
import { CommercialPiService } from './commercial-pi.service';
import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';
import { CommercialBudgetService } from 'app/entities/commercial-budget';

@Component({
    selector: 'jhi-commercial-pi-update',
    templateUrl: './commercial-pi-update.component.html'
})
export class CommercialPiUpdateComponent implements OnInit {
    commercialPi: ICommercialPi;
    isSaving: boolean;

    commercialbudgets: ICommercialBudget[];
    proformaDateDp: any;
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPiService: CommercialPiService,
        protected commercialBudgetService: CommercialBudgetService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialPi }) => {
            this.commercialPi = commercialPi;
            this.createdOn = this.commercialPi.createdOn != null ? this.commercialPi.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.commercialPi.updatedOn != null ? this.commercialPi.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.commercialBudgetService
            .query({ 'commercialPiId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialBudget[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialBudget[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialBudget[]) => {
                    if (!this.commercialPi.commercialBudgetId) {
                        this.commercialbudgets = res;
                    } else {
                        this.commercialBudgetService
                            .find(this.commercialPi.commercialBudgetId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICommercialBudget>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICommercialBudget>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICommercialBudget) => (this.commercialbudgets = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.commercialPi.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.commercialPi.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.commercialPi.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialPiService.update(this.commercialPi));
        } else {
            this.subscribeToSaveResponse(this.commercialPiService.create(this.commercialPi));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialPi>>) {
        result.subscribe((res: HttpResponse<ICommercialPi>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCommercialBudgetById(index: number, item: ICommercialBudget) {
        return item.id;
    }
}
