import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { RequisitionVoucherRelationService } from './requisition-voucher-relation.service';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';

@Component({
    selector: 'jhi-requisition-voucher-relation-update',
    templateUrl: './requisition-voucher-relation-update.component.html'
})
export class RequisitionVoucherRelationUpdateComponent implements OnInit {
    requisitionVoucherRelation: IRequisitionVoucherRelation;
    isSaving: boolean;

    requisitions: IRequisition[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected requisitionVoucherRelationService: RequisitionVoucherRelationService,
        protected requisitionService: RequisitionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ requisitionVoucherRelation }) => {
            this.requisitionVoucherRelation = requisitionVoucherRelation;
        });
        this.requisitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisition[]>) => response.body)
            )
            .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.requisitionVoucherRelation.id !== undefined) {
            this.subscribeToSaveResponse(this.requisitionVoucherRelationService.update(this.requisitionVoucherRelation));
        } else {
            this.subscribeToSaveResponse(this.requisitionVoucherRelationService.create(this.requisitionVoucherRelation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequisitionVoucherRelation>>) {
        result.subscribe(
            (res: HttpResponse<IRequisitionVoucherRelation>) => this.onSaveSuccess(),
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

    trackRequisitionById(index: number, item: IRequisition) {
        return item.id;
    }
}
