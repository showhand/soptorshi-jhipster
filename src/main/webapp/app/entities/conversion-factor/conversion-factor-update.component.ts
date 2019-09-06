import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { ConversionFactorService } from './conversion-factor.service';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';

@Component({
    selector: 'jhi-conversion-factor-update',
    templateUrl: './conversion-factor-update.component.html'
})
export class ConversionFactorUpdateComponent implements OnInit {
    conversionFactor: IConversionFactor;
    isSaving: boolean;

    currencies: ICurrency[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected conversionFactorService: ConversionFactorService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ conversionFactor }) => {
            this.conversionFactor = conversionFactor;
        });
        this.currencyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICurrency[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICurrency[]>) => response.body)
            )
            .subscribe((res: ICurrency[]) => (this.currencies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.conversionFactor.id !== undefined) {
            this.subscribeToSaveResponse(this.conversionFactorService.update(this.conversionFactor));
        } else {
            this.subscribeToSaveResponse(this.conversionFactorService.create(this.conversionFactor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IConversionFactor>>) {
        result.subscribe((res: HttpResponse<IConversionFactor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCurrencyById(index: number, item: ICurrency) {
        return item.id;
    }
}
