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
import { Observable, of } from 'rxjs';

@Component({
    selector: 'jhi-journal-voucher-transactions',
    templateUrl: './journal-voucher-transactions.component.html'
})
export class JournalVoucherTransactionsComponent extends DtTransactionComponent implements OnInit, OnDestroy {
    @Input()
    journalVoucher: IJournalVoucher;
    @Input()
    currency: ICurrency;
    @Input()
    conversionFactor: IConversionFactor;
    @Input()
    voucherType: VoucherType;
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
                'voucherNo.equals': this.journalVoucher.voucherNo
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

    transition() {
        this.loadAll();
    }

    addTransaction() {
        const transaction = new DtTransaction();
        transaction.voucherNo = this.journalVoucher.voucherNo;
        transaction.currencyId = this.journalVoucher.currencyId;
        transaction.convFactor = this.journalVoucher.conversionFactor;
        transaction.voucherDate = this.journalVoucher.voucherDate;
        transaction.fCurrency = this.journalVoucher.currencyId;
        transaction.type =
            this.journalVoucher.type != null
                ? this.journalVoucher.type.toString() === VoucherType.SELLING.toString()
                    ? VoucherType.SELLING
                    : VoucherType.BUYING
                : null;
        const modalRef = this.modalService.open(JournalVoucherTransactionUpdateComponent, { size: 'lg' });
        modalRef.componentInstance.dtTransaction = transaction;
    }

    editTransaction(transaction: DtTransaction) {
        const modalRef = this.modalService.open(JournalVoucherTransactionUpdateComponent, { size: 'lg' });
        modalRef.componentInstance.dtTransaction = transaction;
    }

    deleteTransaction(transaction: DtTransaction) {
        this.dtTransactionService.delete(transaction.id).subscribe((response: any) => {
            this.loadAll();
        });
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
        });
    }

    protected calculateTotalDebitAndCredit(data: IDtTransaction[]): Observable<any> {
        this.totalDebit = 0;
        this.totalCredit = 0;
        data.forEach((d: IDtTransaction) => {
            if (d.balanceType === BalanceType.DEBIT) {
                this.totalDebit = this.totalDebit + d.amount;
            } else if (d.balanceType === BalanceType.CREDIT) {
                this.totalCredit = this.totalCredit + d.amount;
            }
        });
        return of(this.totalCredit);
    }

    registerChangeInDtTransactions() {
        this.eventSubscriber = this.eventManager.subscribe('dtTransactionListModification', response => {
            this.loadAll();
        });
    }
}
