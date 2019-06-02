import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IPayrollManagement } from 'app/shared/model/payroll-management.model';
import { PayrollManagementService } from './payroll-management.service';

@Component({
    selector: 'jhi-payroll-management-update',
    templateUrl: './payroll-management-update.component.html'
})
export class PayrollManagementUpdateComponent implements OnInit {
    payrollManagement: IPayrollManagement;
    isSaving: boolean;

    constructor(protected payrollManagementService: PayrollManagementService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ payrollManagement }) => {
            this.payrollManagement = payrollManagement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {}

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayrollManagement>>) {
        result.subscribe((res: HttpResponse<IPayrollManagement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
