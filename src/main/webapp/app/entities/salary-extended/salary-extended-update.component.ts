import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISalary } from 'app/shared/model/salary.model';
import { SalaryExtendedService } from './salary-extended.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { SalaryUpdateComponent } from 'app/entities/salary';

@Component({
    selector: 'jhi-salary-update',
    templateUrl: './salary-extended-update.component.html'
})
export class SalaryExtendedUpdateComponent extends SalaryUpdateComponent implements OnInit {
    startedOnDp: any;
    endedOnDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected salaryService: SalaryExtendedService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, salaryService, employeeService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ salary }) => {
            this.salary = salary;
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
