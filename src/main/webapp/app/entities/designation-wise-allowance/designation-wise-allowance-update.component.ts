import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';
import { DesignationWiseAllowanceService } from './designation-wise-allowance.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation';
import { AccountService } from 'app/core';

@Component({
    selector: 'jhi-designation-wise-allowance-update',
    templateUrl: './designation-wise-allowance-update.component.html'
})
export class DesignationWiseAllowanceUpdateComponent implements OnInit {
    designationWiseAllowance: IDesignationWiseAllowance;
    isSaving: boolean;

    designations: IDesignation[];
    modifiedOnDp: any;
    currentAccount: Account;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected designationWiseAllowanceService: DesignationWiseAllowanceService,
        protected designationService: DesignationService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected router: Router
    ) {}

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });

        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ designationWiseAllowance }) => {
            this.designationWiseAllowance = designationWiseAllowance;
            this.accountService.identity().then(account => {
                this.designationWiseAllowance.modifiedBy = account.toString();
            });
        });
        this.designationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDesignation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDesignation[]>) => response.body)
            )
            .subscribe((res: IDesignation[]) => (this.designations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.designationWiseAllowance.id !== undefined) {
            this.subscribeToSaveResponse(this.designationWiseAllowanceService.update(this.designationWiseAllowance));
        } else {
            this.subscribeToSaveResponse(this.designationWiseAllowanceService.create(this.designationWiseAllowance));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDesignationWiseAllowance>>) {
        result.subscribe(
            (res: HttpResponse<IDesignationWiseAllowance>) => this.onSaveSuccess(),
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDesignationById(index: number, item: IDesignation) {
        return item.id;
    }
}
