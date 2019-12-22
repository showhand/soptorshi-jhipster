import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MstAccountExtendedService } from 'app/entities/mst-account-extended';
import { DtTransactionExtendedService, GeneralLedgerFetchType } from 'app/entities/dt-transaction-extended/dt-transaction-extended.service';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { merge, Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IMstAccount, MstAccount } from 'app/shared/model/mst-account.model';
import { GroupType } from 'app/shared/model/system-group-map.model';
import { FinancialAccountYearExtendedService } from 'app/entities/financial-account-year-extended';
import { FinancialYearStatus, IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import * as moment from 'moment';

@Component({
    selector: 'jh-general-ledger-report',
    templateUrl: './general-ledger-report.component.html'
})
export class GeneralLedgerReportComponent implements OnInit, OnDestroy {
    generalLedgerFetchType: GeneralLedgerFetchType;
    accountId: number;
    accountNameList: string[] = [];
    mstAccounts: MstAccount[];
    accountNameMapAccount: any;
    selectedAccountName: string;
    fromDate: any;
    toDate: any;
    specification: any;

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    constructor(
        private mstAccountExtendedService: MstAccountExtendedService,
        private dtTransactionService: DtTransactionExtendedService,
        private financialAccountYearService: FinancialAccountYearExtendedService
    ) {}

    ngOnDestroy(): void {}

    accountSelected() {
        const selectedAccount: IMstAccount = this.accountNameMapAccount[this.selectedAccountName];
        this.accountId = selectedAccount.id;
    }

    ngOnInit(): void {
        this.generalLedgerFetchType = GeneralLedgerFetchType.TRANSACTION_SPECIFIC;

        this.financialAccountYearService
            .query({
                'status.equals': FinancialYearStatus.ACTIVE
            })
            .subscribe((res: HttpResponse<IFinancialAccountYear[]>) => {
                const financialAccountYear = res.body[0];
                this.fromDate = financialAccountYear.startDate;
                this.toDate = moment();
            });

        this.mstAccountExtendedService
            .query({
                size: 50000
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IMstAccount[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMstAccount[]>) => response.body)
            )
            .subscribe((res: IMstAccount[]) => {
                this.mstAccounts = [];
                this.accountNameList = [];
                this.accountNameMapAccount = {};
                res.forEach((a: IMstAccount) => {
                    this.mstAccounts.push(a);
                    const accountName = a.name + ' (' + a.groupName + ')';
                    this.accountNameList.push(accountName);
                    this.accountNameMapAccount[accountName] = a;
                });
            });
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

    downloadReport() {
        this.dtTransactionService.downloadGeneralLedgerReport(
            this.generalLedgerFetchType,
            this.accountId == null ? 9999 : this.accountId,
            this.fromDate,
            this.toDate
        );
    }
}
