import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanManagement } from 'app/shared/model/loan-management.model';

@Component({
    selector: 'jhi-loan-management-detail',
    templateUrl: './loan-management-detail.component.html'
})
export class LoanManagementDetailComponent implements OnInit {
    loanManagement: ILoanManagement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ loanManagement }) => {
            this.loanManagement = loanManagement;
        });
    }

    previousState() {
        window.history.back();
    }
}
