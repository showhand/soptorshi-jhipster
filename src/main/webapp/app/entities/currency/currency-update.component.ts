import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from './currency.service';

@Component({
    selector: 'jhi-currency-update',
    templateUrl: './currency-update.component.html'
})
export class CurrencyUpdateComponent implements OnInit {
    currency: ICurrency;
    isSaving: boolean;
    modifiedOnDp: any;

    constructor(protected currencyService: CurrencyService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ currency }) => {
            this.currency = currency;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.currency.id !== undefined) {
            this.subscribeToSaveResponse(this.currencyService.update(this.currency));
        } else {
            this.subscribeToSaveResponse(this.currencyService.create(this.currency));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrency>>) {
        result.subscribe((res: HttpResponse<ICurrency>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
