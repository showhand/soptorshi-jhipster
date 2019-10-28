import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { ICreditorLedger } from 'app/shared/model/creditor-ledger.model';
import { CreditorLedgerService } from 'app/entities/creditor-ledger';
import { IDebtorLedger } from 'app/shared/model/debtor-ledger.model';
import { DebtorLedgerService } from 'app/entities/debtor-ledger';
import { IChequeRegister } from 'app/shared/model/cheque-register.model';
import { ChequeRegisterService } from 'app/entities/cheque-register';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';
import { DtTransactionService, DtTransactionUpdateComponent } from 'app/entities/dt-transaction';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-journal-voucher-transaction-update',
    templateUrl: './journal-voucher-transaction-update.component.html'
})
export class JournalVoucherTransactionUpdateComponent extends DtTransactionUpdateComponent implements OnInit {
    dtTransaction: IDtTransaction;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dtTransactionService: DtTransactionService,
        protected mstAccountService: MstAccountService,
        protected voucherService: VoucherService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute,
        protected activeModal: NgbActiveModal,
        protected jhiEventManager: JhiEventManager
    ) {
        super(jhiAlertService, dtTransactionService, mstAccountService, voucherService, currencyService, activatedRoute);
    }

    previousState() {
        this.jhiEventManager.broadcast({
            name: 'dtTransactionListModification',
            content: 'updated an dtTransaction'
        });
        this.activeModal.dismiss(true);
    }

    ngOnInit() {
        this.isSaving = false;

        this.mstAccountService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMstAccount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstAccount[]>) => response.body)
            )
            .subscribe((res: IMstAccount[]) => (this.mstaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.voucherService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVoucher[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVoucher[]>) => response.body)
            )
            .subscribe((res: IVoucher[]) => (this.vouchers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.currencyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICurrency[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICurrency[]>) => response.body)
            )
            .subscribe((res: ICurrency[]) => (this.currencies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }
}
