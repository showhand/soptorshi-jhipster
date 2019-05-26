import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoan } from 'app/shared/model/loan.model';

@Component({
    selector: 'jhi-loan-detail',
    templateUrl: './loan-detail.component.html'
})
export class LoanDetailComponent implements OnInit {
    loan: ILoan;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ loan }) => {
            this.loan = loan;
        });
    }

    previousState() {
        window.history.back();
    }
}
