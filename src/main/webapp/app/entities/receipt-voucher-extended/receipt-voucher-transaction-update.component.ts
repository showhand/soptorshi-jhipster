import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { merge, Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';
import { DtTransactionService, DtTransactionUpdateComponent } from 'app/entities/dt-transaction';
import { NgbActiveModal, NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { GroupType, ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { SystemGroupMapService } from 'app/entities/system-group-map';

@Component({
    selector: 'jhi-dt-transaction-update',
    templateUrl: './receipt-voucher-transaction-update.component.html'
})
export class ReceiptVoucherTransactionUpdateComponent extends DtTransactionUpdateComponent implements OnInit {
    @Input()
    dtTransaction: IDtTransaction;

    groupTypeWithSystemGroupMap: any;
    accountNameList: string[] = [];
    accountNameMapAccount: any;
    selectedAccountName: string;
    showInvoice: boolean;
    showCheque: boolean;

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected dtTransactionService: DtTransactionService,
        protected mstAccountService: MstAccountService,
        protected voucherService: VoucherService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute,
        protected activeModal: NgbActiveModal,
        protected jhiEventManager: JhiEventManager,
        protected systemGroupMapService: SystemGroupMapService
    ) {
        super(jhiAlertService, dtTransactionService, mstAccountService, voucherService, currencyService, activatedRoute);
    }

    accountSelected() {
        const selectedAccount: IMstAccount = this.accountNameMapAccount[this.selectedAccountName];
        if (
            this.groupTypeWithSystemGroupMap[GroupType.SUNDRY_DEBTOR] &&
            selectedAccount.groupId == this.groupTypeWithSystemGroupMap[GroupType.SUNDRY_DEBTOR].groupId
        ) {
            this.showInvoice = true;
            this.showCheque = false;
        } else if (
            this.groupTypeWithSystemGroupMap[GroupType.SUNDRY_CREDITOR] &&
            selectedAccount.groupId == this.groupTypeWithSystemGroupMap[GroupType.SUNDRY_CREDITOR].groupId
        ) {
            this.showCheque = true;
            this.showInvoice = false;
        } else {
            this.showCheque = false;
            this.showInvoice = false;
        }

        this.dtTransaction.accountId = selectedAccount.id;
    }

    fetchBankAndCostTypeGroups() {
        this.systemGroupMapService
            .query({
                size: 100
            })
            .subscribe(
                (response: HttpResponse<ISystemGroupMap[]>) => {
                    this.groupTypeWithSystemGroupMap = {};
                    response.body.forEach((s: ISystemGroupMap) => {
                        this.groupTypeWithSystemGroupMap[s.groupType] = s;
                    });
                },
                (response: HttpErrorResponse) => {
                    console.log(response.message);
                },
                () => {
                    this.mstAccountService
                        .query({
                            size: 50000
                        })
                        .pipe(
                            filter((mayBeOk: HttpResponse<IMstAccount[]>) => mayBeOk.ok),
                            map((response: HttpResponse<IMstAccount[]>) => response.body)
                        )
                        .subscribe(
                            (res: IMstAccount[]) => {
                                this.mstaccounts = [];
                                this.accountNameList = [];
                                this.accountNameMapAccount = {};
                                res.forEach((a: IMstAccount) => {
                                    if (
                                        a.groupId !== this.groupTypeWithSystemGroupMap[GroupType.BANK_ACCOUNTS].groupId &&
                                        a.groupId !== this.groupTypeWithSystemGroupMap[GroupType.CASH_IN_HAND].groupId
                                    ) {
                                        this.mstaccounts.push(a);
                                        const accountName = a.name + ' (' + a.groupName + ')';
                                        this.accountNameList.push(accountName);
                                        this.accountNameMapAccount[accountName] = a;
                                        if (a.id == this.dtTransaction.accountId) this.selectedAccountName = accountName;
                                    }
                                });
                            },
                            (res: HttpErrorResponse) => this.onError(res.message)
                        );
                }
            );
    }

    ngOnInit() {
        this.isSaving = false;
        this.fetchBankAndCostTypeGroups();
    }

    previousState() {
        this.jhiEventManager.broadcast({
            name: 'dtTransactionListModification',
            content: 'Deleted an dtTransaction'
        });
        this.activeModal.dismiss(true);
    }

    save() {
        this.isSaving = true;
        if (this.dtTransaction.id !== undefined) {
            this.subscribeToSaveResponse(this.dtTransactionService.update(this.dtTransaction));
        } else {
            this.subscribeToSaveResponse(this.dtTransactionService.create(this.dtTransaction));
        }
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    search = (text$: Observable<string>) => {
        const debouncedText$ = text$.pipe(
            debounceTime(200),
            distinctUntilChanged()
        );
        const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
        const inputFocus$ = this.focus$;

        return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
            map(term =>
                (term === ''
                    ? this.accountNameList
                    : this.accountNameList.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)
                ).slice(0, 10)
            )
        );
    };
}
