import { Component, OnInit } from '@angular/core';
import { ILeaveApplication, LeaveStatus } from 'app/shared/model/leave-application.model';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { Account, AccountService } from 'app/core';
import { JhiAlertService } from 'ng-jhipster';
import { LeaveTypeService } from 'app/entities/leave-type';
import { ActivatedRoute, Router } from '@angular/router';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ILeaveBalance } from 'app/shared/model/leave-balance.model';
import { LeaveBalanceService } from 'app/entities/leave-balance';
import { LeaveApplicationExtendedService } from 'app/entities/leave-application-extended/leave-application-extended.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { ManagerService } from 'app/entities/manager';
import { IManager } from 'app/shared/model/manager.model';

@Component({
    selector: 'jhi-others-leave-application',
    templateUrl: './others-leave-application.component.html'
})
export class OthersLeaveApplicationComponent implements OnInit {
    leaveApplication: ILeaveApplication;
    isSaving: boolean;
    leaveBalance: ILeaveBalance;

    message: string;
    warning: string;

    leavetypes: ILeaveType[];
    fromDateDp: any;
    toDateDp: any;
    appliedOn: string;
    actionTakenOn: string;

    account: Account;
    employees: IEmployee[];
    candidate: IEmployee;
    currentEmployee: IEmployee;
    employeesUnderSupervisor: IManager[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected leaveApplicationService: LeaveApplicationExtendedService,
        protected leaveTypeService: LeaveTypeService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected leaveBalanceService: LeaveBalanceService,
        protected router: Router,
        protected employeeService: EmployeeService,
        protected managerService: ManagerService
    ) {}

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.account = account;
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
                    'employeeId.equals': this.account.login
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                    map((response: HttpResponse<IEmployee[]>) => response.body)
                )
                .subscribe((res: IEmployee[]) => (this.currentEmployee = res[0]), (res: HttpErrorResponse) => this.onError(res.message));
            if (this.accountService.hasAnyAuthority(['ROLE_ADMIN']) || this.accountService.hasAnyAuthority(['ROLE_LEAVE_ADMIN'])) {
                this.employeeService
                    .query()
                    .pipe(
                        filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                        map((response: HttpResponse<IEmployee[]>) => response.body)
                    )
                    .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
            } else {
                this.employeeService
                    .query({
                        'employeeId.equals': this.account.login
                    })
                    .subscribe(
                        (res: HttpResponse<IEmployee[]>) => {
                            this.currentEmployee = res.body[0];
                            this.managerService
                                .query({
                                    'employeeId.equals': this.currentEmployee.id
                                })
                                .subscribe(
                                    (res: HttpResponse<IManager[]>) => {
                                        this.employeesUnderSupervisor = res.body;
                                        const map: string = this.employeesUnderSupervisor.map(val => val.parentEmployeeId).join(',');
                                        this.employeeService
                                            .query({
                                                'id.in': [map]
                                            })
                                            .subscribe(
                                                (res: HttpResponse<IEmployee[]>) => (this.employees = res.body),
                                                (res: HttpErrorResponse) => this.onError(res.message)
                                            );
                                    },
                                    (res: HttpErrorResponse) => this.onError(res.message)
                                );
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            }
        });
    }

    previousState() {
        window.history.back();
    }

    attachmentState() {
        this.router.navigate(['/leave-attachment/new']);
    }

    editState(res: HttpResponse<ILeaveApplication>) {
        this.router.navigate(['/leave-application/' + res.body.id + '/edit']);
    }

    save() {
        this.isSaving = true;
        this.leaveApplication.status = LeaveStatus.WAITING;
        this.leaveApplication.appliedByIdId = this.currentEmployee.id;
        this.leaveApplication.appliedOn = this.appliedOn != null ? moment(this.appliedOn, DATE_TIME_FORMAT) : null;
        this.leaveApplication.actionTakenOn = this.actionTakenOn != null ? moment(this.actionTakenOn, DATE_TIME_FORMAT) : null;
        if (this.leaveApplication.id !== undefined) {
            this.subscribeToSaveResponse(this.leaveApplicationService.update(this.leaveApplication));
        } else {
            this.subscribeToSaveResponse(this.leaveApplicationService.create(this.leaveApplication));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveApplication>>) {
        result.subscribe((res: HttpResponse<ILeaveApplication>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess(res: HttpResponse<ILeaveApplication>) {
        this.isSaving = false;
        this.editState(res);
        // this.previousState();
    }

    calculateDiff() {
        if (this.leaveApplication.fromDate && this.leaveApplication.toDate) {
            this.leaveApplicationService
                .calculateDifference(this.leaveApplication.fromDate.format(DATE_FORMAT), this.leaveApplication.toDate.format(DATE_FORMAT))
                .subscribe(res => (this.leaveApplication.numberOfDays = res.body), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    protected onSaveError() {
        this.isSaving = false;
        this.onError('Error while saving!');
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackLeaveTypeById(index: number, item: ILeaveType) {
        return item.id;
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }

    fetchLeaveBalance() {
        if (
            this.leaveApplication.employeesId &&
            this.leaveApplication.leaveTypesId &&
            this.leaveApplication.fromDate &&
            this.leaveApplication.toDate
        ) {
            this.employeeService
                .find(this.leaveApplication.employeesId)
                .pipe(
                    filter((mayBeOk: HttpResponse<IEmployee>) => mayBeOk.ok),
                    map((response: HttpResponse<IEmployee>) => response.body)
                )
                .subscribe((res: IEmployee) => (this.candidate = res), (res: HttpErrorResponse) => this.onError(res.message));

            this.message = '';
            this.warning = '';

            const fromYear: number = this.leaveApplication.fromDate.year();
            const toYear: number = this.leaveApplication.toDate.year();

            if (fromYear === toYear) {
                this.leaveBalanceService.getOne(this.candidate.employeeId, fromYear, this.leaveApplication.leaveTypesId).subscribe(
                    (res: HttpResponse<ILeaveBalance>) => {
                        this.leaveBalance = res.body;
                        this.message =
                            this.leaveBalance.leaveTypeName +
                            ': Remaining leave ' +
                            this.leaveBalance.remainingDays +
                            ' out of ' +
                            this.leaveBalance.totalLeaveApplicableDays +
                            ' day(s)';

                        if (this.leaveApplication.numberOfDays > this.leaveBalance.remainingDays) {
                            this.warning = this.leaveBalance.leaveTypeName + ': Out of balance!! ' + 'Sorry, leave can not be applied.';
                        }
                    },
                    (res: HttpErrorResponse) => (this.message = 'Error!! while fetching leave history.')
                );
            } else {
                this.message = 'Dates should have the same year.';
            }
        }
    }

    calculateDifference() {
        this.leaveApplication.numberOfDays =
            this.leaveApplication.toDate && this.leaveApplication.fromDate
                ? this.leaveApplication.toDate.diff(this.leaveApplication.fromDate, 'days') + 1
                : 0;
    }
}
