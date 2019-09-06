import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionService } from './dt-transaction.service';
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

@Component({
    selector: 'jhi-dt-transaction-update',
    templateUrl: './dt-transaction-update.component.html'
})
export class DtTransactionUpdateComponent implements OnInit {
    dtTransaction: IDtTransaction;
    isSaving: boolean;

    creditorledgers: ICreditorLedger[];

    debtorledgers: IDebtorLedger[];

    chequeregisters: IChequeRegister[];

    mstaccounts: IMstAccount[];

    vouchers: IVoucher[];

    currencies: ICurrency[];
    voucherDateDp: any;
    invoiceDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dtTransactionService: DtTransactionService,
        protected creditorLedgerService: CreditorLedgerService,
        protected debtorLedgerService: DebtorLedgerService,
        protected chequeRegisterService: ChequeRegisterService,
        protected mstAccountService: MstAccountService,
        protected voucherService: VoucherService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dtTransaction }) => {
            this.dtTransaction = dtTransaction;
        });
        this.creditorLedgerService
            .query({ 'dtTransactionId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICreditorLedger[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICreditorLedger[]>) => response.body)
            )
            .subscribe(
                (res: ICreditorLedger[]) => {
                    if (!this.dtTransaction.creditorLedgerId) {
                        this.creditorledgers = res;
                    } else {
                        this.creditorLedgerService
                            .find(this.dtTransaction.creditorLedgerId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICreditorLedger>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICreditorLedger>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICreditorLedger) => (this.creditorledgers = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.debtorLedgerService
            .query({ 'dtTransactionId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IDebtorLedger[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDebtorLedger[]>) => response.body)
            )
            .subscribe(
                (res: IDebtorLedger[]) => {
                    if (!this.dtTransaction.debtorLedgerId) {
                        this.debtorledgers = res;
                    } else {
                        this.debtorLedgerService
                            .find(this.dtTransaction.debtorLedgerId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IDebtorLedger>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IDebtorLedger>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IDebtorLedger) => (this.debtorledgers = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.chequeRegisterService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IChequeRegister[]>) => mayBeOk.ok),
                map((response: HttpResponse<IChequeRegister[]>) => response.body)
            )
            .subscribe((res: IChequeRegister[]) => (this.chequeregisters = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dtTransaction.id !== undefined) {
            this.subscribeToSaveResponse(this.dtTransactionService.update(this.dtTransaction));
        } else {
            this.subscribeToSaveResponse(this.dtTransactionService.create(this.dtTransaction));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDtTransaction>>) {
        result.subscribe((res: HttpResponse<IDtTransaction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCreditorLedgerById(index: number, item: ICreditorLedger) {
        return item.id;
    }

    trackDebtorLedgerById(index: number, item: IDebtorLedger) {
        return item.id;
    }

    trackChequeRegisterById(index: number, item: IChequeRegister) {
        return item.id;
    }

    trackMstAccountById(index: number, item: IMstAccount) {
        return item.id;
    }

    trackVoucherById(index: number, item: IVoucher) {
        return item.id;
    }

    trackCurrencyById(index: number, item: ICurrency) {
        return item.id;
    }
}
