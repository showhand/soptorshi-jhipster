import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyExtendedService } from './currency-extended.service';
import { CurrencyUpdateComponent } from 'app/entities/currency';

@Component({
    selector: 'jhi-currency-update',
    templateUrl: './currency-extended-update.component.html'
})
export class CurrencyExtendedUpdateComponent extends CurrencyUpdateComponent implements OnInit {
    constructor(protected currencyService: CurrencyExtendedService, protected activatedRoute: ActivatedRoute) {
        super(currencyService, activatedRoute);
    }
}
