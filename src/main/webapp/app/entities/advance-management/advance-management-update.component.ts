import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAdvanceManagement } from 'app/shared/model/advance-management.model';
import { AdvanceManagementService } from './advance-management.service';

@Component({
    selector: 'jhi-advance-management-update',
    templateUrl: './advance-management-update.component.html'
})
export class AdvanceManagementUpdateComponent implements OnInit {
    advanceManagement: IAdvanceManagement;
    isSaving: boolean;

    constructor(protected advanceManagementService: AdvanceManagementService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ advanceManagement }) => {
            this.advanceManagement = advanceManagement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.advanceManagement.id !== undefined) {
            this.subscribeToSaveResponse(this.advanceManagementService.update(this.advanceManagement));
        } else {
            this.subscribeToSaveResponse(this.advanceManagementService.create(this.advanceManagement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdvanceManagement>>) {
        result.subscribe((res: HttpResponse<IAdvanceManagement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
