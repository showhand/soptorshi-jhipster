import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { from, Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILeaveApplication, LeaveStatus } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from './leave-application.service';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type';
import { Account, AccountService } from 'app/core';
import { ILeaveBalance } from 'app/shared/model/leave-balance.model';
import { LeaveBalanceService } from 'app/entities/leave-balance';
import { Moment } from 'moment';

@Component({
    selector: 'jhi-leave-application-update',
    templateUrl: './leave-application-update.component.html'
})
export class LeaveApplicationUpdateComponent implements OnInit {
    leaveApplication: ILeaveApplication;
    leaveBalance: ILeaveBalance;
    isSaving: boolean;
    message: string;
    warning: string;

    leavetypes: ILeaveType[];
    fromDateDp: any;
    toDateDp: any;
    appliedOn: string;
    actionTakenOn: string;

    account: Account;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected leaveApplicationService: LeaveApplicationService,
        protected leaveBalanceService: LeaveBalanceService,
        protected leaveTypeService: LeaveTypeService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected router: Router
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
        });
    }

    previousState() {
        /*window.history.back();*/
        this.router.navigate(['/leave-application']);
    }

    editState(res: HttpResponse<ILeaveApplication>) {
        this.router.navigate(['/leave-application/' + res.body.id + '/edit']);
    }

    attachmentState() {
        this.router.navigate(['/leave-attachment/new']);
    }

    save() {
        this.isSaving = true;
        this.leaveApplication.employeeId = '701001';
        this.leaveApplication.appliedBy = this.account.login;
        this.leaveApplication.actionTakenBy = '';
        this.leaveApplication.status = LeaveStatus.WAITING;
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
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackLeaveTypeById(index: number, item: ILeaveType) {
        return item.id;
    }

    fetchLeaveBalance() {
        if (this.leaveApplication.leaveTypesId && this.leaveApplication.fromDate && this.leaveApplication.toDate) {
            this.message = '';
            this.warning = '';

            let fromYear: number = this.leaveApplication.fromDate.year();
            let toYear: number = this.leaveApplication.toDate.year();

            if (fromYear === toYear) {
                this.leaveBalanceService.getOne(this.account.login, fromYear, this.leaveApplication.leaveTypesId).subscribe(
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
                            this.warning =
                                this.leaveBalance.leaveTypeName + ': Out of balance!! ' + 'Sorry, you can not apply for this leave.';
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
