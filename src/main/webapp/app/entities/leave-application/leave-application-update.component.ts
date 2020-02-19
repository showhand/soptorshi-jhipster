import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from './leave-application.service';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-leave-application-update',
    templateUrl: './leave-application-update.component.html'
})
export class LeaveApplicationUpdateComponent implements OnInit {
    leaveApplication: ILeaveApplication;
    isSaving: boolean;

    leavetypes: ILeaveType[];

    employees: IEmployee[];
    fromDateDp: any;
    toDateDp: any;
    appliedOn: string;
    actionTakenOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected leaveApplicationService: LeaveApplicationService,
        protected leaveTypeService: LeaveTypeService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
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

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }
}
