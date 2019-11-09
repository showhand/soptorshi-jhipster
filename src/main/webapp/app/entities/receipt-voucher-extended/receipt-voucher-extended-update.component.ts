import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';
import { ReceiptVoucherExtendedService } from 'app/entities/receipt-voucher-extended/receipt-voucher-extended.service';
import { ReceiptVoucherUpdateComponent } from 'app/entities/receipt-voucher';
import { SystemGroupMapService } from 'app/entities/system-group-map';
import { GroupType, ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { DtTransactionExtendedService } from 'app/entities/dt-transaction-extended';

@Component({
    selector: 'jhi-receipt-voucher-update',
    templateUrl: './receipt-voucher-extended-update.component.html'
})
export class ReceiptVoucherExtendedUpdateComponent extends ReceiptVoucherUpdateComponent implements OnInit {
    totalAmount: number;
    bankAndCashGroupIds: number[];
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected receiptVoucherService: ReceiptVoucherExtendedService,
        protected mstAccountService: MstAccountService,
        protected activatedRoute: ActivatedRoute,
        protected systemGroupMapService: SystemGroupMapService,
        protected dtTransactionService: DtTransactionExtendedService
    ) {
        super(jhiAlertService, receiptVoucherService, mstAccountService, activatedRoute);
    }

    loadAll() {
        this.systemGroupMapService
            .query({
                'groupType.in': [GroupType.BANK_ACCOUNTS, GroupType.CASH_IN_HAND]
            })
            .subscribe((response: HttpResponse<ISystemGroupMap[]>) => {
                this.bankAndCashGroupIds = [];
                response.body.forEach((s: ISystemGroupMap) => {
                    this.bankAndCashGroupIds.push(s.groupId);
                });

                this.mstAccountService
                    .query({
                        'groupId.in': this.bankAndCashGroupIds,
                        size: 200
                    })
                    .pipe(
                        filter((mayBeOk: HttpResponse<IMstAccount[]>) => mayBeOk.ok),
                        map((response: HttpResponse<IMstAccount[]>) => response.body)
                    )
                    .subscribe((res: IMstAccount[]) => (this.mstaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
            });
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ receiptVoucher }) => {
            this.receiptVoucher = receiptVoucher;
        });
        this.loadAll();
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
        result.subscribe(
            (res: HttpResponse<IReceiptVoucher>) => {
                this.receiptVoucher = res.body;
                this.onSaveSuccess();
            },
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
    }

    public calculateTotalAmount(totalAmount: number) {
        this.totalAmount = totalAmount;
    }

    post() {
        this.receiptVoucher.postDate = moment();
        this.save();
    }

    downloadVoucherReport() {
        this.dtTransactionService.downloadVoucherReport('RECEIPT VOUCHER', this.receiptVoucher.voucherNo, this.receiptVoucher.voucherDate);
    }
}
