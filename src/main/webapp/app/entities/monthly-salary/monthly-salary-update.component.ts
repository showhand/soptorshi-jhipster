import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from './monthly-salary.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-monthly-salary-update',
    templateUrl: './monthly-salary-update.component.html'
})
export class MonthlySalaryUpdateComponent implements OnInit {
    monthlySalary: IMonthlySalary;
    isSaving: boolean;

    employees: IEmployee[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected monthlySalaryService: MonthlySalaryService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ monthlySalary }) => {
            this.monthlySalary = monthlySalary;
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

    save() {
        this.isSaving = true;
        if (this.monthlySalary.id !== undefined) {
            this.subscribeToSaveResponse(this.monthlySalaryService.update(this.monthlySalary));
        } else {
            this.subscribeToSaveResponse(this.monthlySalaryService.create(this.monthlySalary));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMonthlySalary>>) {
        result.subscribe((res: HttpResponse<IMonthlySalary>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
