import { Component, OnInit } from '@angular/core';
import { ILeaveApplication, LeaveStatus } from 'app/shared/model/leave-application.model';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { Account, AccountService } from 'app/core';
import { JhiAlertService } from 'ng-jhipster';
import { LeaveApplicationService } from 'app/entities/leave-application/leave-application.service';
import { LeaveTypeService } from 'app/entities/leave-type';
import { ActivatedRoute } from '@angular/router';
import { DATE_TIME_FORMAT } from 'app/shared';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ILeaveBalance } from 'app/shared/model/leave-balance.model';
import { LeaveBalanceService } from 'app/entities/leave-balance';

@Component({
    selector: 'jhi-others-leave-application',
    templateUrl: './others-leave-application.component.html',
    styles: []
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

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected leaveApplicationService: LeaveApplicationService,
        protected leaveTypeService: LeaveTypeService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected leaveBalanceService: LeaveBalanceService
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
        window.history.back();
    }

    clear() {
        this.leaveApplication = <ILeaveApplication>{};
    }

    save() {
        this.isSaving = true;
        this.leaveApplication.appliedBy = this.account.login;
        this.leaveApplication.actionTakenBy = this.account.login;
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
        result.subscribe((res: HttpResponse<ILeaveApplication>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.clear();
        // this.previousState();
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
        if (
            this.leaveApplication.employeeId &&
            this.leaveApplication.leaveTypesId &&
            this.leaveApplication.fromDate &&
            this.leaveApplication.toDate
        ) {
            this.message = '';
            this.warning = '';

            let fromYear: number = this.leaveApplication.fromDate.year();
            let toYear: number = this.leaveApplication.toDate.year();

            if (fromYear === toYear) {
                this.leaveBalanceService.getOne(this.leaveApplication.employeeId, fromYear, this.leaveApplication.leaveTypesId).subscribe(
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
