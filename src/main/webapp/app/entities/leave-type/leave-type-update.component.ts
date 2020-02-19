import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from './leave-type.service';

@Component({
    selector: 'jhi-leave-type-update',
    templateUrl: './leave-type-update.component.html'
})
export class LeaveTypeUpdateComponent implements OnInit {
    leaveType: ILeaveType;
    isSaving: boolean;
    createdOn: string;
    updatedOn: string;

    constructor(protected leaveTypeService: LeaveTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ leaveType }) => {
            this.leaveType = leaveType;
            this.createdOn = this.leaveType.createdOn != null ? this.leaveType.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.leaveType.updatedOn != null ? this.leaveType.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.leaveType.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.leaveType.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.leaveType.id !== undefined) {
            this.subscribeToSaveResponse(this.leaveTypeService.update(this.leaveType));
        } else {
            this.subscribeToSaveResponse(this.leaveTypeService.create(this.leaveType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveType>>) {
        result.subscribe((res: HttpResponse<ILeaveType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
