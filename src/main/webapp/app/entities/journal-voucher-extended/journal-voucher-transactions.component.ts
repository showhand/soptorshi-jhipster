import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { DtTransactionComponent } from 'app/entities/dt-transaction';
import { DtTransactionExtendedService } from 'app/entities/dt-transaction-extended';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { ICurrency } from 'app/shared/model/currency.model';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { DtTransaction, IDtTransaction, VoucherType } from 'app/shared/model/dt-transaction.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JournalVoucherTransactionUpdateComponent } from 'app/entities/journal-voucher-extended/journal-voucher-transaction-update.component';

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
                (res: HttpResponse<IDtTransaction[]>) => this.paginateDtTransactions(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    addTransaction() {
        let transaction = new DtTransaction();
        transaction.voucherNo = this.journalVoucher.voucherNo;
        transaction.currencyId = this.currency.id;
        transaction.convFactor = this.conversionFactor.bcovFactor;
        transaction.voucherDate = this.journalVoucher.voucherDate;
        transaction.currencyNotation = this.currency.notation;
        transaction.fCurrency = this.currency.id;
        transaction.type = this.voucherType;
        let modalRef = this.modalService.open(JournalVoucherTransactionUpdateComponent, { size: 'lg' });
        modalRef.componentInstance.dtTransaction = transaction;
    }

    registerChangeInDtTransactions() {
        this.eventSubscriber = this.eventManager.subscribe('dtTransactionListModification', response => {
            this.loadAll();
        });
    }
}
