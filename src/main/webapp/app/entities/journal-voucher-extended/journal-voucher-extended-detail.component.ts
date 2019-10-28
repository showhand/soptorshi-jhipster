import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherDetailComponent } from 'app/entities/journal-voucher';
import { CurrencyFlag, ICurrency } from 'app/shared/model/currency.model';
import { JournalVoucherExtendedService } from 'app/entities/journal-voucher-extended/journal-voucher-extended.service';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { CurrencyExtendedService } from 'app/entities/currency-extended';
import { ConversionFactorExtendedService } from 'app/entities/conversion-factor-extended';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { VoucherType } from 'app/shared/model/dt-transaction.model';

@Component({
    selector: 'jhi-journal-voucher-detail',
    templateUrl: './journal-voucher-extended-detail.component.html'
})
export class JournalVoucherExtendedDetailComponent extends JournalVoucherDetailComponent implements OnInit {
    totalDebit: number;
    totalCredit: number;
    currencies: ICurrency[];
    selectedCurrency: ICurrency;
    conversionFactor: IConversionFactor;
    isSaving: boolean;
    voucherType: VoucherType;
    journalVoucherId: number;

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected journalVoucherService: JournalVoucherExtendedService,
        protected currencyService: CurrencyExtendedService,
        protected conversionFactorService: ConversionFactorExtendedService
    ) {
        super(activatedRoute);
        this.totalCredit = 0;
        this.totalDebit = 0;
        this.isSaving = true;
    }

    ngOnInit() {
        super.ngOnInit();
        this.loadAll();
        this.journalVoucher.type = this.voucherType == null ? (this.voucherType = VoucherType.BUYING) : this.voucherType;
    }

    loadAll() {
        this.journalVoucherId = this.journalVoucher.id;
        this.currencyService
            .query({
                size: 60
            })
            .subscribe((response: HttpResponse<ICurrency[]>) => {
                this.currencies = response.body;
                if (!this.journalVoucher.currencyId) {
                    this.currencies.forEach((c: ICurrency) => {
                        if (c.flag === CurrencyFlag.BASE && !this.journalVoucher.currencyId) {
                            this.journalVoucher.currencyId = c.id;
                            this.fetchConversionFactor();
                        }
                    });
                }
            });
    }

    fetchConversionFactor() {
        this.conversionFactorService
            .query({
                'currencyId.equals': this.journalVoucher.id,
                sort: ['modifiedOn,desc']
            })
            .subscribe((response: HttpResponse<IConversionFactor[]>) => {
                this.journalVoucher.conversionFactor = response.body[0].bcovFactor;
            });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.journalVoucher.id !== undefined) {
            this.journalVoucherService.update(this.journalVoucher);
        } else {
            this.journalVoucherService.create(this.journalVoucher);
        }
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
