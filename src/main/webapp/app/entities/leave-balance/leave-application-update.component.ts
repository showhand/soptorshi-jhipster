import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILeaveApplication, LeaveStatus } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from './leave-application.service';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type';
import { Account, AccountService } from 'app/core';

@Component({
    selector: 'jhi-leave-application-update',
    templateUrl: './leave-application-update.component.html'
})
export class LeaveApplicationUpdateComponent implements OnInit {
    leaveApplication: ILeaveApplication;
    isSaving: boolean;

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

    save() {
        this.isSaving = true;
        this.leaveApplication.employeeId = this.account.login;
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
        this.previousState();
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

    calculateDifference() {
        this.leaveApplication.numberOfDays =
            this.leaveApplication.toDate && this.leaveApplication.fromDate
                ? this.leaveApplication.toDate.diff(this.leaveApplication.fromDate, 'days') + 1
                : 0;
    }
}
