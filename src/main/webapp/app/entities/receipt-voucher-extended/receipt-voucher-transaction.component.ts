import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { BalanceType, DtTransaction, IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { AccountService } from 'app/core';
import { DtTransactionComponent, DtTransactionService } from 'app/entities/dt-transaction';
import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ReceiptVoucherTransactionUpdateComponent } from 'app/entities/receipt-voucher-extended/receipt-voucher-transaction-update.component';
import { Observable, of } from 'rxjs';

@Component({
    selector: 'jhi-receipt-voucher-transaction',
    templateUrl: './receipt-voucher-transaction.component.html'
})
export class ReceiptVoucherTransactionComponent extends DtTransactionComponent implements OnInit, OnDestroy {
    @Input()
    receiptVoucher: IReceiptVoucher;
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
                'voucherNo.equals': this.receiptVoucher.voucherNo,
                'balanceType.equals': BalanceType.CREDIT,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.predicate,
                'voucherDate.equals': this.receiptVoucher.voucherDate.format('YYYY-MM-DD')
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

    addTransaction() {
        let transaction = new DtTransaction();
        transaction.voucherNo = this.receiptVoucher.voucherNo;
        transaction.voucherDate = this.receiptVoucher.voucherDate;
        transaction.convFactor = 1;
        transaction.balanceType = BalanceType.CREDIT;
        let modalRef = this.modalService.open(ReceiptVoucherTransactionUpdateComponent, { size: 'lg' });
        modalRef.componentInstance.dtTransaction = transaction;
    }

    transition() {
        this.loadAll();
    }

    deleteTransaction(dtTransaction: IDtTransaction) {
        this.dtTransactionService.delete(dtTransaction.id).subscribe((response: any) => {
            this.loadAll();
        });
    }

    editTransaction(transaction: DtTransaction) {
        let modalRef = this.modalService.open(ReceiptVoucherTransactionUpdateComponent, { size: 'lg' });
        modalRef.componentInstance.dtTransaction = transaction;
    }
}
