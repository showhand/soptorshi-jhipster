import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILoan } from 'app/shared/model/loan.model';
import { LoanService } from './loan.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-loan-update',
    templateUrl: './loan-update.component.html'
})
export class LoanUpdateComponent implements OnInit {
    loan: ILoan;
    isSaving: boolean;

    employees: IEmployee[];
    takenOnDp: any;
    modifiedDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected loanService: LoanService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ loan }) => {
            this.loan = loan;
        });
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    amountChanged() {
        this.loan.left = this.loan.amount;
    }

    save() {
        this.isSaving = true;
        if (this.loan.id !== undefined) {
            this.subscribeToSaveResponse(this.loanService.update(this.loan));
        } else {
            this.subscribeToSaveResponse(this.loanService.create(this.loan));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoan>>) {
        result.subscribe((res: HttpResponse<ILoan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }
}
