import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { ReceiptVoucherService } from './receipt-voucher.service';

@Component({
    selector: 'jhi-receipt-voucher-update',
    templateUrl: './receipt-voucher-update.component.html'
})
export class ReceiptVoucherUpdateComponent implements OnInit {
    receiptVoucher: IReceiptVoucher;
    isSaving: boolean;
    voucherDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;

    constructor(protected receiptVoucherService: ReceiptVoucherService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ receiptVoucher }) => {
            this.receiptVoucher = receiptVoucher;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.receiptVoucher.id !== undefined) {
            this.subscribeToSaveResponse(this.receiptVoucherService.update(this.receiptVoucher));
        } else {
            this.subscribeToSaveResponse(this.receiptVoucherService.create(this.receiptVoucher));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReceiptVoucher>>) {
        result.subscribe((res: HttpResponse<IReceiptVoucher>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
