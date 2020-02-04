import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';
import { RequisitionVoucherRelationService } from './requisition-voucher-relation.service';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { JournalVoucherService } from 'app/entities/journal-voucher';
import { ContraVoucherService } from 'app/entities/contra-voucher';
import { ReceiptVoucherService } from 'app/entities/receipt-voucher';
import { PaymentVoucherService } from 'app/entities/payment-voucher';
import { ApplicationType } from 'app/shared/model/journal-voucher.model';

@Component({
    selector: 'jhi-requisition-voucher-relation-update',
    templateUrl: './requisition-voucher-relation-update.component.html'
})
export class RequisitionVoucherRelationUpdateComponent implements OnInit {
    requisitionVoucherRelation: IRequisitionVoucherRelation;
    isSaving: boolean;

    vouchers: IVoucher[];

    requisitions: IRequisition[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected requisitionVoucherRelationService: RequisitionVoucherRelationService,
        protected voucherService: VoucherService,
        protected requisitionService: RequisitionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ requisitionVoucherRelation }) => {
            this.requisitionVoucherRelation = requisitionVoucherRelation;
            console.log('------------>');
            console.log(this.requisitionVoucherRelation);
        });
        this.voucherService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVoucher[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVoucher[]>) => response.body)
            )
            .subscribe((res: IVoucher[]) => (this.vouchers = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackVoucherById(index: number, item: IVoucher) {
        return item.id;
    }

    trackRequisitionById(index: number, item: IRequisition) {
        return item.id;
    }
}
