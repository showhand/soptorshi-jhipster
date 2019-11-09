import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { PaymentVoucherService, PaymentVoucherUpdateComponent } from 'app/entities/payment-voucher';
import { PaymentVoucherExtendedService } from 'app/entities/payment-voucher-extended/payment-voucher-extended.service';
import { JhiAlertService } from 'ng-jhipster';
import { MstAccountService } from 'app/entities/mst-account';
import { IAccountBalance } from 'app/shared/model/account-balance.model';
import { AccountBalanceService } from 'app/entities/account-balance';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { FinancialAccountYearExtendedService } from 'app/entities/financial-account-year-extended';
import { FinancialYearStatus, IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { SystemGroupMapService } from 'app/entities/system-group-map';
import { GroupType, ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { DtTransactionExtendedService } from 'app/entities/dt-transaction-extended';

@Component({
    selector: 'jhi-payment-voucher-update',
    templateUrl: './payment-voucher-extended-update.component.html'
})
export class PaymentVoucherExtendedUpdateComponent extends PaymentVoucherUpdateComponent implements OnInit {
    paymentVoucher: IPaymentVoucher;
    isSaving: boolean;
    voucherDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;
    totalAmount: number;
    bankAccountBalance: IAccountBalance;
    openedFinancialAccountYear: IFinancialAccountYear;
    bankAndCashGroupIds: number[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected paymentVoucherService: PaymentVoucherExtendedService,
        protected mstAccountService: MstAccountService,
        protected activatedRoute: ActivatedRoute,
        protected accountBalanceService: AccountBalanceService,
        protected financialAccountYearService: FinancialAccountYearExtendedService,
        protected systemGroupMapService: SystemGroupMapService,
        protected dtTransactionService: DtTransactionExtendedService
    ) {
        super(jhiAlertService, paymentVoucherService, mstAccountService, activatedRoute);
    }

    loadAll() {
        this.systemGroupMapService
            .query({
                'groupType.in': [GroupType.BANK_ACCOUNTS, GroupType.CASH_IN_HAND]
            })
            .subscribe((response: HttpResponse<ISystemGroupMap[]>) => {
                this.bankAndCashGroupIds = [];
                response.body.forEach((s: ISystemGroupMap) => {
                    this.bankAndCashGroupIds.push(s.groupId);
                });

                this.mstAccountService
                    .query({
                        'groupId.in': this.bankAndCashGroupIds,
                        size: 200
                    })
                    .pipe(
                        filter((mayBeOk: HttpResponse<IMstAccount[]>) => mayBeOk.ok),
                        map((response: HttpResponse<IMstAccount[]>) => response.body)
                    )
                    .subscribe((res: IMstAccount[]) => (this.mstaccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
            });

        this.financialAccountYearService
            .query({
                'status.equals': FinancialYearStatus.ACTIVE
            })
            .subscribe((response: HttpResponse<IFinancialAccountYear[]>) => {
                this.openedFinancialAccountYear = response.body[0];
                if (this.paymentVoucher.accountId) this.accountSelected();
            });
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paymentVoucher }) => {
            this.paymentVoucher = paymentVoucher;
        });
        this.loadAll();
    }

    calculateTotalAmount(totalAmount: number) {
        this.totalAmount = totalAmount;
    }

    accountSelected() {
        if (this.paymentVoucher.accountId) {
            this.accountBalanceService
                .query({
                    'accountId.equals': this.paymentVoucher.accountId,
                    'financialAccountYearId.equals': this.openedFinancialAccountYear.id
                })
                .subscribe((response: HttpResponse<IAccountBalance[]>) => {
                    this.bankAccountBalance = response.body[0];
                });
        }
    }

    save() {
        this.isSaving = true;
        if (this.paymentVoucher.id !== undefined) {
            this.subscribeToSaveResponse(this.paymentVoucherService.update(this.paymentVoucher));
        } else {
            this.subscribeToSaveResponse(this.paymentVoucherService.create(this.paymentVoucher));
        }
    }

    post() {
        this.paymentVoucher.postDate = moment();
        this.save();
    }

    downloadVoucherReport() {
        this.dtTransactionService.downloadVoucherReport('PAYMENT VOUCHER', this.paymentVoucher.voucherNo, this.paymentVoucher.voucherDate);
    }
}
