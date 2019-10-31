import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeExtendedService } from './leave-type-extended.service';
import { LeaveTypeUpdateComponent } from 'app/entities/leave-type';

@Component({
    selector: 'jhi-leave-type-update-extended',
    templateUrl: './leave-type-update-extended.component.html'
})
export class LeaveTypeUpdateExtendedComponent extends LeaveTypeUpdateComponent implements OnInit {
    leaveType: ILeaveType;
    isSaving: boolean;

    constructor(protected leaveTypeService: LeaveTypeExtendedService, protected activatedRoute: ActivatedRoute) {
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
