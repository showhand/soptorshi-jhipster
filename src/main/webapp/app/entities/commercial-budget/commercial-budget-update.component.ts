import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';
import { CommercialBudgetService } from './commercial-budget.service';

@Component({
    selector: 'jhi-commercial-budget-update',
    templateUrl: './commercial-budget-update.component.html'
})
export class CommercialBudgetUpdateComponent implements OnInit {
    commercialBudget: ICommercialBudget;
    isSaving: boolean;
    budgetDateDp: any;
    createdOn: string;
    updatedOn: string;

    constructor(protected commercialBudgetService: CommercialBudgetService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialBudget }) => {
            this.commercialBudget = commercialBudget;
            this.createdOn = this.commercialBudget.createdOn != null ? this.commercialBudget.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.commercialBudget.updatedOn != null ? this.commercialBudget.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.commercialBudget.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.commercialBudget.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.commercialBudget.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialBudgetService.update(this.commercialBudget));
        } else {
            this.subscribeToSaveResponse(this.commercialBudgetService.create(this.commercialBudget));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialBudget>>) {
        result.subscribe((res: HttpResponse<ICommercialBudget>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
