import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { ConversionFactorExtendedService } from './conversion-factor-extended.service';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';
import { ConversionFactorUpdateComponent } from 'app/entities/conversion-factor';

@Component({
    selector: 'jhi-conversion-factor-update',
    templateUrl: './conversion-factor-extended-update.component.html'
})
export class ConversionFactorExtendedUpdateComponent extends ConversionFactorUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected conversionFactorService: ConversionFactorExtendedService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, conversionFactorService, currencyService, activatedRoute);
    }
}
