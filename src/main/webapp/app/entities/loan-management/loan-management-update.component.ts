import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ILoanManagement } from 'app/shared/model/loan-management.model';
import { LoanManagementService } from './loan-management.service';

@Component({
    selector: 'jhi-loan-management-update',
    templateUrl: './loan-management-update.component.html'
})
export class LoanManagementUpdateComponent implements OnInit {
    loanManagement: ILoanManagement;
    isSaving: boolean;

    constructor(protected loanManagementService: LoanManagementService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ loanManagement }) => {
            this.loanManagement = loanManagement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.loanManagement.id !== undefined) {
            this.subscribeToSaveResponse(this.loanManagementService.update(this.loanManagement));
        } else {
            this.subscribeToSaveResponse(this.loanManagementService.create(this.loanManagement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanManagement>>) {
        result.subscribe((res: HttpResponse<ILoanManagement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
