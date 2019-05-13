import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';

@Component({
    selector: 'jhi-financial-account-year-detail',
    templateUrl: './financial-account-year-detail.component.html'
})
export class FinancialAccountYearDetailComponent implements OnInit {
    financialAccountYear: IFinancialAccountYear;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ financialAccountYear }) => {
            this.financialAccountYear = financialAccountYear;
        });
    }

    previousState() {
        window.history.back();
    }
}
