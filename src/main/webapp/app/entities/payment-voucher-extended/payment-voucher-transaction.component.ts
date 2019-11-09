import { Component, OnInit, OnDestroy, Input, Output, EventEmitter } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, of, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { BalanceType, DtTransaction, IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { DtTransactionExtendedService } from 'app/entities/dt-transaction-extended';
import { DtTransactionComponent, DtTransactionService } from 'app/entities/dt-transaction';
import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { ReceiptVoucherTransactionUpdateComponent } from 'app/entities/receipt-voucher-extended/receipt-voucher-transaction-update.component';
import { PaymentVoucherTransactionUpdateComponent } from 'app/entities/payment-voucher-extended/payment-voucher-transaction-update.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-payment-voucher-transaction',
    templateUrl: './payment-voucher-transaction.component.html'
})
export class PaymentVoucherTransactionComponent extends DtTransactionComponent implements OnInit, OnDestroy {
    @Input()
    paymentVoucher: IPaymentVoucher;
    @Output()
    totalAmountChanged = new EventEmitter<number>();
    totalAmount: number;

    constructor(
        protected dtTransactionService: DtTransactionService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected modalService: NgbModal
    ) {
        super(dtTransactionService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
        this.page = 0;
        this.previousPage = 0;
        this.predicate = ['id,asc'];
    }

    loadAll() {
        this.dtTransactionService
            .query({
                'voucherNo.equals': this.paymentVoucher.voucherNo,
                'balanceType.equals': BalanceType.DEBIT,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.predicate,
                'voucherDate.equals': this.paymentVoucher.voucherDate.format('YYYY-MM-DD')
            })
            .subscribe(
                (res: HttpResponse<IDtTransaction[]>) => {
                    this.countTotalAmount(res.body).subscribe((response: any) => {
                        this.totalAmountChanged.emit(this.totalAmount);
                    });
                    this.paginateDtTransactions(res.body, res.headers);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    countTotalAmount(data: IDtTransaction[]): Observable<number> {
        this.totalAmount = 0;
        data.forEach((r: IDtTransaction) => {
            this.totalAmount = this.totalAmount + r.amount;
        });
        return of(this.totalAmount);
    }

    transition() {
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDtTransactions();
    }

    addTransaction() {
        let transaction = new DtTransaction();
        transaction.voucherNo = this.paymentVoucher.voucherNo;
        transaction.voucherDate = this.paymentVoucher.voucherDate;
        transaction.convFactor = 1;
        transaction.balanceType = BalanceType.DEBIT;
        let modalRef = this.modalService.open(PaymentVoucherTransactionUpdateComponent, { size: 'lg' });
        modalRef.componentInstance.dtTransaction = transaction;
    }

    deleteTransaction(dtTransaction: IDtTransaction) {
        this.dtTransactionService.delete(dtTransaction.id).subscribe((response: any) => {
            this.loadAll();
        });
    }

    editTransaction(transaction: DtTransaction) {
        let modalRef = this.modalService.open(PaymentVoucherTransactionUpdateComponent, { size: 'lg' });
        modalRef.componentInstance.dtTransaction = transaction;
    }
}
