import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPayrollManagement } from 'app/shared/model/payroll-management.model';

@Component({
    selector: 'jhi-payroll-management-detail',
    templateUrl: './payroll-management-detail.component.html'
})
export class PayrollManagementDetailComponent implements OnInit {
    payrollManagement: IPayrollManagement;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ payrollManagement }) => {
            this.payrollManagement = payrollManagement;
        });
    }

    previousState() {
        window.history.back();
    }
}
