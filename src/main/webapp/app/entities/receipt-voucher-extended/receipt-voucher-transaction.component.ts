import { Component, OnInit, OnDestroy, Input, Output } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { BalanceType, IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { DtTransactionComponent, DtTransactionService } from 'app/entities/dt-transaction';
import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';

@Component({
    selector: 'jhi-receipt-voucher-transaction',
    templateUrl: './receipt-voucher-transaction.component.html'
})
export class ReceiptVoucherTransactionComponent extends DtTransactionComponent implements OnInit, OnDestroy {
    @Input()
    receiptVoucher: IReceiptVoucher;
    @Input()
    totalAmount: number;
    constructor(
        protected dtTransactionService: DtTransactionService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(dtTransactionService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
        this.page = 0;
        this.previousPage = 0;
        this.predicate = ['id,asc'];
    }

    loadAll() {
        this.dtTransactionService
            .query({
                'voucherNo.equals': this.receiptVoucher.voucherNo,
                'balanceType.equals': BalanceType.CREDIT,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.predicate,
                'voucherDate.equals': this.receiptVoucher.voucherDate.format('YYYY-MM-DD')
            })
            .subscribe(
                (res: HttpResponse<IDtTransaction[]>) => {
                    this.totalAmount = 0;
                    res.body.forEach((r: IDtTransaction) => {
                        this.totalAmount = this.totalAmount + r.amount;
                    });
                    this.paginateDtTransactions(res.body, res.headers);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    deleteTransaction(dtTransaction: IDtTransaction) {
        this.dtTransactionService.delete(dtTransaction.id).subscribe((response: any) => {
            this.loadAll();
        });
    }
}
