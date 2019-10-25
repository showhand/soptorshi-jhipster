import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyDetailComponent } from 'app/entities/currency';

@Component({
    selector: 'jhi-currency-detail',
    templateUrl: './currency-extended-detail.component.html'
})
export class CurrencyExtendedDetailComponent extends CurrencyDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
