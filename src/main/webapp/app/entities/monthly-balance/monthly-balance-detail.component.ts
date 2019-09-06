import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonthlyBalance } from 'app/shared/model/monthly-balance.model';

@Component({
    selector: 'jhi-monthly-balance-detail',
    templateUrl: './monthly-balance-detail.component.html'
})
export class MonthlyBalanceDetailComponent implements OnInit {
    monthlyBalance: IMonthlyBalance;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlyBalance }) => {
            this.monthlyBalance = monthlyBalance;
        });
    }

    previousState() {
        window.history.back();
    }
}
