import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';
import { SalaryVoucherRelationService } from './salary-voucher-relation.service';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office';

@Component({
    selector: 'jhi-salary-voucher-relation-update',
    templateUrl: './salary-voucher-relation-update.component.html'
})
export class SalaryVoucherRelationUpdateComponent implements OnInit {
    salaryVoucherRelation: ISalaryVoucherRelation;
    isSaving: boolean;

    offices: IOffice[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected salaryVoucherRelationService: SalaryVoucherRelationService,
        protected officeService: OfficeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ salaryVoucherRelation }) => {
            this.salaryVoucherRelation = salaryVoucherRelation;
        });
        this.officeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOffice[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOffice[]>) => response.body)
            )
            .subscribe((res: IOffice[]) => (this.offices = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.salaryVoucherRelation.id !== undefined) {
            this.subscribeToSaveResponse(this.salaryVoucherRelationService.update(this.salaryVoucherRelation));
        } else {
            this.subscribeToSaveResponse(this.salaryVoucherRelationService.create(this.salaryVoucherRelation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalaryVoucherRelation>>) {
        result.subscribe(
            (res: HttpResponse<ISalaryVoucherRelation>) => this.onSaveSuccess(),
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

    trackOfficeById(index: number, item: IOffice) {
        return item.id;
    }
}
