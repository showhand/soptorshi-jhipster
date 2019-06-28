import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from './leave-type.service';

@Component({
    selector: 'jhi-leave-type-update',
    templateUrl: './leave-type-update.component.html'
})
export class LeaveTypeUpdateComponent implements OnInit {
    leaveType: ILeaveType;
    isSaving: boolean;

    constructor(protected leaveTypeService: LeaveTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ leaveType }) => {
            this.leaveType = leaveType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
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
