import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { ReceiptVoucherService } from './receipt-voucher.service';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';

@Component({
    selector: 'jhi-receipt-voucher-update',
    templateUrl: './receipt-voucher-update.component.html'
})
export class ReceiptVoucherUpdateComponent implements OnInit {
    receiptVoucher: IReceiptVoucher;
    isSaving: boolean;

    mstaccounts: IMstAccount[];
    voucherDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected receiptVoucherService: ReceiptVoucherService,
        protected mstAccountService: MstAccountService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ receiptVoucher }) => {
            this.receiptVoucher = receiptVoucher;
        });
        this.mstAccountService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMstAccount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstAccount[]>) => response.body)
            )
            .subscribe((res: IMstAccount[]) => (this.mstaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackMstAccountById(index: number, item: IMstAccount) {
        return item.id;
    }
}
