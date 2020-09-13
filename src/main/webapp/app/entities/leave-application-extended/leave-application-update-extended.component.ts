import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { LeaveApplicationExtendedService } from './leave-application-extended.service';
import { LeaveTypeService } from 'app/entities/leave-type';
import { EmployeeService } from 'app/entities/employee';
import { LeaveApplicationUpdateComponent } from 'app/entities/leave-application';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared';
import * as moment from 'moment';
import { LeaveStatus } from 'app/shared/model/leave-application.model';
import { filter, map } from 'rxjs/operators';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { Employee, IEmployee } from 'app/shared/model/employee.model';
import { Account, AccountService } from 'app/core';

@Component({
    selector: 'jhi-leave-application-update-extended',
    templateUrl: './leave-application-update-extended.component.html'
})
export class LeaveApplicationUpdateExtendedComponent extends LeaveApplicationUpdateComponent implements OnInit {
    currentAccount: Account;
    currentEmployee: Employee;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected leaveApplicationService: LeaveApplicationExtendedService,
        protected leaveTypeService: LeaveTypeService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(jhiAlertService, leaveApplicationService, leaveTypeService, employeeService, activatedRoute);
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.isSaving = false;
            this.activatedRoute.data.subscribe(({ leaveApplication }) => {
                this.leaveApplication = leaveApplication;
                this.appliedOn = this.leaveApplication.appliedOn != null ? this.leaveApplication.appliedOn.format(DATE_TIME_FORMAT) : null;
                this.actionTakenOn =
                    this.leaveApplication.actionTakenOn != null ? this.leaveApplication.actionTakenOn.format(DATE_TIME_FORMAT) : null;
            });
            this.leaveTypeService
                .query()
                .pipe(
                    filter((mayBeOk: HttpResponse<ILeaveType[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ILeaveType[]>) => response.body)
                )
                .subscribe((res: ILeaveType[]) => (this.leavetypes = res), (res: HttpErrorResponse) => this.onError(res.message));
            this.employeeService
                .query({
                    'employeeId.equals': this.currentAccount.login
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                    map((response: HttpResponse<IEmployee[]>) => response.body)
                )
                .subscribe((res: IEmployee[]) => (this.currentEmployee = res[0]), (res: HttpErrorResponse) => this.onError(res.message));
        });
    }

    calculateDiff() {
        if (this.leaveApplication.fromDate && this.leaveApplication.toDate) {
            this.leaveApplicationService
                .calculateDifference(this.leaveApplication.fromDate.format(DATE_FORMAT), this.leaveApplication.toDate.format(DATE_FORMAT))
                .subscribe(res => (this.leaveApplication.numberOfDays = res.body), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    save() {
        this.isSaving = true;
        this.leaveApplication.status = LeaveStatus.WAITING;
        this.leaveApplication.employeesId = this.currentEmployee.id;
        this.leaveApplication.appliedByIdId = this.currentEmployee.id;
        this.leaveApplication.appliedOn =
            this.appliedOn != null ? moment(this.appliedOn, DATE_TIME_FORMAT) : moment(new Date(), DATE_TIME_FORMAT);
        this.leaveApplication.actionTakenOn = this.actionTakenOn != null ? moment(this.actionTakenOn, DATE_TIME_FORMAT) : null;
        if (this.leaveApplication.id !== undefined) {
            this.subscribeToSaveResponse(this.leaveApplicationService.update(this.leaveApplication));
        } else {
            this.subscribeToSaveResponse(this.leaveApplicationService.create(this.leaveApplication));
        }
    }
}
