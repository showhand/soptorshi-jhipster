import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { DtTransactionComponent } from 'app/entities/dt-transaction';
import { DtTransactionExtendedService } from 'app/entities/dt-transaction-extended';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { ICurrency } from 'app/shared/model/currency.model';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BalanceType, DtTransaction, IDtTransaction, VoucherType } from 'app/shared/model/dt-transaction.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JournalVoucherTransactionUpdateComponent } from 'app/entities/journal-voucher-extended/journal-voucher-transaction-update.component';
import { observable, Observable, of } from 'rxjs';
import { IContraVoucher } from 'app/shared/model/contra-voucher.model';
import { ContraVoucherTransactionUpdateComponent } from 'app/entities/contra-voucher-extended/contra-voucher-transaction-update.component';

@Component({
    selector: 'jhi-contra-voucher-transactions',
    templateUrl: './contra-voucher-transactions.component.html'
})
export class ContraVoucherTransactionsComponent extends DtTransactionComponent implements OnInit, OnDestroy {
    @Input()
    contraVoucher: IContraVoucher;
    totalDebit: number;
    totalCredit: number;
    @Output()
    totalDebitChanged = new EventEmitter<number>();
    @Output()
    totalCreditChanged = new EventEmitter<number>();

    constructor(
        protected dtTransactionService: DtTransactionExtendedService,
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
                size: this.itemsPerPage,
                'voucherNo.equals': this.contraVoucher.voucherNo
            })
            .subscribe(
                (res: HttpResponse<IDtTransaction[]>) => {
                    this.paginateDtTransactions(res.body, res.headers);
                },
                (res: HttpErrorResponse) => {
                    this.onError(res.message);
                }
            );
    }

    addTransaction() {
        let transaction = new DtTransaction();
        transaction.voucherNo = this.contraVoucher.voucherNo;
        transaction.currencyId = this.contraVoucher.currencyId;
        transaction.convFactor = this.contraVoucher.conversionFactor;
        transaction.voucherDate = this.contraVoucher.voucherDate;
        transaction.fCurrency = this.contraVoucher.currencyId;
        let modalRef = this.modalService.open(ContraVoucherTransactionUpdateComponent, { size: 'lg' });
        modalRef.componentInstance.dtTransaction = transaction;
    }

    editTransaction(transaction: DtTransaction) {
        let modalRef = this.modalService.open(ContraVoucherTransactionUpdateComponent, { size: 'lg' });
        modalRef.componentInstance.dtTransaction = transaction;
    }

    deleteTransaction(transaction: DtTransaction) {
        this.dtTransactionService.delete(transaction.id).subscribe((response: any) => {
            this.loadAll();
        });
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

    protected paginateDtTransactions(data: IDtTransaction[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.dtTransactions = data;
        this.calculateTotalDebitAndCredit(data).subscribe((value: number) => {
            this.totalDebitChanged.emit(this.totalDebit);
            this.totalCreditChanged.emit(this.totalCredit);
            console.log('TOtal debit--->' + this.totalDebit);
            console.log('Total Credit --->' + this.totalCredit);
        });
    }

    protected calculateTotalDebitAndCredit(data: IDtTransaction[]): Observable<any> {
        this.totalDebit = 0;
        this.totalCredit = 0;
        data.forEach((d: IDtTransaction) => {
            if (d.balanceType == BalanceType.DEBIT) this.totalDebit = this.totalDebit + d.amount;
            else if (d.balanceType == BalanceType.CREDIT) this.totalCredit = this.totalCredit + d.amount;
        });
        return of(this.totalCredit);
    }

    registerChangeInDtTransactions() {
        this.eventSubscriber = this.eventManager.subscribe('dtTransactionListModification', response => {
            this.loadAll();
        });
    }
}
