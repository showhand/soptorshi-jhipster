import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IFineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';
import { FineAdvanceLoanManagementService } from './fine-advance-loan-management.service';

@Component({
    selector: 'jhi-fine-advance-loan-management-update',
    templateUrl: './fine-advance-loan-management-update.component.html'
})
export class FineAdvanceLoanManagementUpdateComponent implements OnInit {
    fineAdvanceLoanManagement: IFineAdvanceLoanManagement;
    isSaving: boolean;

    constructor(protected fineAdvanceLoanManagementService: FineAdvanceLoanManagementService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fineAdvanceLoanManagement }) => {
            this.fineAdvanceLoanManagement = fineAdvanceLoanManagement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fineAdvanceLoanManagement.id !== undefined) {
            this.subscribeToSaveResponse(this.fineAdvanceLoanManagementService.update(this.fineAdvanceLoanManagement));
        } else {
            this.subscribeToSaveResponse(this.fineAdvanceLoanManagementService.create(this.fineAdvanceLoanManagement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFineAdvanceLoanManagement>>) {
        result.subscribe(
            (res: HttpResponse<IFineAdvanceLoanManagement>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
