import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMontlySalaryVouchers } from 'app/shared/model/montly-salary-vouchers.model';

@Component({
    selector: 'jhi-montly-salary-vouchers-detail',
    templateUrl: './montly-salary-vouchers-detail.component.html'
})
export class MontlySalaryVouchersDetailComponent implements OnInit {
    montlySalaryVouchers: IMontlySalaryVouchers;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ montlySalaryVouchers }) => {
            this.montlySalaryVouchers = montlySalaryVouchers;
        });
    }

    previousState() {
        window.history.back();
    }
}
