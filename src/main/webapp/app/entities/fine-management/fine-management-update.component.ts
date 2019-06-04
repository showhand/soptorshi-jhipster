import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IFineManagement } from 'app/shared/model/fine-management.model';
import { FineManagementService } from './fine-management.service';

@Component({
    selector: 'jhi-fine-management-update',
    templateUrl: './fine-management-update.component.html'
})
export class FineManagementUpdateComponent implements OnInit {
    fineManagement: IFineManagement;
    isSaving: boolean;

    constructor(protected fineManagementService: FineManagementService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fineManagement }) => {
            this.fineManagement = fineManagement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fineManagement.id !== undefined) {
            this.subscribeToSaveResponse(this.fineManagementService.update(this.fineManagement));
        } else {
            this.subscribeToSaveResponse(this.fineManagementService.create(this.fineManagement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFineManagement>>) {
        result.subscribe((res: HttpResponse<IFineManagement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
