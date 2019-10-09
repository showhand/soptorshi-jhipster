import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearDetailComponent } from 'app/entities/financial-account-year';

@Component({
    selector: 'jhi-financial-account-year-detail',
    templateUrl: './financial-account-year-extended-detail.component.html'
})
export class FinancialAccountYearExtendedDetailComponent extends FinancialAccountYearDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
