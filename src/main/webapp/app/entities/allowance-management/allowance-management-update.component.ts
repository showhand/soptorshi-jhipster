import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAllowanceManagement } from 'app/shared/model/allowance-management.model';
import { AllowanceManagementService } from './allowance-management.service';

@Component({
    selector: 'jhi-allowance-management-update',
    templateUrl: './allowance-management-update.component.html'
})
export class AllowanceManagementUpdateComponent implements OnInit {
    allowanceManagement: IAllowanceManagement;
    isSaving: boolean;

    constructor(protected allowanceManagementService: AllowanceManagementService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ allowanceManagement }) => {
            this.allowanceManagement = allowanceManagement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {}

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAllowanceManagement>>) {
        result.subscribe((res: HttpResponse<IAllowanceManagement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
