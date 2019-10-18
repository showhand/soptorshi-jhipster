import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeServiceExtended } from './leave-type.service.extended';
import { LeaveTypeUpdateComponent } from 'app/entities/leave-type';

@Component({
    selector: 'jhi-leave-type-update-extended',
    templateUrl: './leave-type-update.component.extended.html'
})
export class LeaveTypeUpdateComponentExtended extends LeaveTypeUpdateComponent implements OnInit {
    leaveType: ILeaveType;
    isSaving: boolean;

    constructor(protected leaveTypeService: LeaveTypeServiceExtended, protected activatedRoute: ActivatedRoute) {
        super(leaveTypeService, activatedRoute);
    }

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
