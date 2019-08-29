import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { MonthlySalaryService, MonthlySalaryUpdateComponent } from 'app/entities/monthly-salary';

@Component({
    selector: 'jhi-monthly-salary-update',
    templateUrl: './monthly-salary-extended-update.component.html'
})
export class MonthlySalaryExtendedUpdateComponent extends MonthlySalaryUpdateComponent implements OnInit {
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected monthlySalaryService: MonthlySalaryService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, monthlySalaryService, employeeService, activatedRoute);
    }

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
}
