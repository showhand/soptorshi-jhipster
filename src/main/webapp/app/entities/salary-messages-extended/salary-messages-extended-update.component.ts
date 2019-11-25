import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISalaryMessages } from 'app/shared/model/salary-messages.model';
import { SalaryMessagesExtendedService } from './salary-messages-extended.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from 'app/entities/monthly-salary';
import { SalaryMessagesUpdateComponent } from 'app/entities/salary-messages';

@Component({
    selector: 'jhi-salary-messages-update',
    templateUrl: './salary-messages-extended-update.component.html'
})
export class SalaryMessagesExtendedUpdateComponent extends SalaryMessagesUpdateComponent implements OnInit {
    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected salaryMessagesService: SalaryMessagesExtendedService,
        protected employeeService: EmployeeService,
        protected monthlySalaryService: MonthlySalaryService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(dataUtils, jhiAlertService, salaryMessagesService, employeeService, monthlySalaryService, activatedRoute);
    }
}
