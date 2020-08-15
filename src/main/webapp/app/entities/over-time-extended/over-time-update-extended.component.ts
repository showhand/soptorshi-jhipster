import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { OverTimeExtendedService } from './over-time-extended.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { OverTimeUpdateComponent } from 'app/entities/over-time';
import { IManager } from 'app/shared/model/manager.model';
import { Account, AccountService } from 'app/core';
import { ManagerService } from 'app/entities/manager';
import { filter, map } from 'rxjs/operators';
import { EmployeeExtendedService } from 'app/entities/employee-extended';

@Component({
    selector: 'jhi-over-time-update-extended',
    templateUrl: './over-time-update-extended.component.html'
})
export class OverTimeUpdateExtendedComponent extends OverTimeUpdateComponent {
    currentAccount: Account;
    currentEmployee: IEmployee;
    employeesUnderSupervisor: IManager[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected overTimeService: OverTimeExtendedService,
        protected employeeService: EmployeeExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected managerService: ManagerService
    ) {
        super(jhiAlertService, overTimeService, employeeService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ overTime }) => {
            this.overTime = overTime;
            this.fromTime = this.overTime.fromTime != null ? this.overTime.fromTime.format(DATE_TIME_FORMAT) : null;
            this.toTime = this.overTime.toTime != null ? this.overTime.toTime.format(DATE_TIME_FORMAT) : null;
            this.createdOn = this.overTime.createdOn != null ? this.overTime.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.overTime.updatedOn != null ? this.overTime.updatedOn.format(DATE_TIME_FORMAT) : null;
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
