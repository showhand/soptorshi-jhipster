import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JournalVoucherDetailComponent } from 'app/entities/journal-voucher';
import { CurrencyFlag, ICurrency } from 'app/shared/model/currency.model';
import { JournalVoucherExtendedService } from 'app/entities/journal-voucher-extended/journal-voucher-extended.service';
import { HttpResponse } from '@angular/common/http';
import { CurrencyExtendedService } from 'app/entities/currency-extended';
import { ConversionFactorExtendedService } from 'app/entities/conversion-factor-extended';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import * as moment from 'moment';
import { VoucherType } from 'app/shared/model/journal-voucher.model';

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
        protected conversionFactorService: ConversionFactorExtendedService,
        protected eventManager: JhiEventManager,
        protected jhiAlertService: JhiAlertService,
        protected router: Router
    ) {
        super(activatedRoute);
        this.totalCredit = 0;
        this.totalDebit = 0;
        this.isSaving = true;
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ journalVoucher }) => {
            this.journalVoucher = journalVoucher;
            this.loadAll();
            this.journalVoucher.type = this.voucherType === null ? VoucherType.SELLING : this.voucherType;
        });
    }

    loadAll() {
        this.journalVoucherId = this.journalVoucher.id;
        this.currencyService
            .query({
                size: 60
            })
            .subscribe((response: HttpResponse<ICurrency[]>) => {
                this.currencies = response.body;
                if (!this.journalVoucher.currencyId == undefined) {
                    this.currencies.forEach((c: ICurrency) => {
                        if (c.flag == CurrencyFlag.BASE) {
                            this.journalVoucher.currencyId = c.id;
                            this.fetchConversionFactor();
                        }
                    });
                } else {
                    this.fetchConversionFactor();
                }
            });
    }

    fetchConversionFactor() {
        this.conversionFactorService
            .query({
                'currencyId.equals': this.journalVoucher.currencyId,
                sort: ['modifiedOn,desc']
            })
            .subscribe((response: HttpResponse<IConversionFactor[]>) => {
                this.journalVoucher.conversionFactor = response.body[0].bcovFactor;
            });
    }

    previousState() {
        this.eventManager.broadcast({
            name: 'journalVoucherListModification',
            content: 'Deleted an journalVoucher'
        });
        window.history.back();
    }

    post() {
        this.journalVoucher.postDate = moment();
        this.save();
    }

    save() {
        this.isSaving = true;
        if (this.totalDebit == 0 && this.totalCredit == 0) {
            this.jhiAlertService.error('Total Debit or credit must not be Zero');
        } else if (this.totalCredit != this.totalDebit) {
            this.jhiAlertService.error('Total Debit and Credit must be equal in Journal Voucher', '', 'bottom');
        } else {
            if (this.journalVoucher.id !== undefined) {
                this.journalVoucherService.update(this.journalVoucher).subscribe((response: any) => {});
            } else {
                this.journalVoucherService.create(this.journalVoucher).subscribe((response: any) => {});
            }
        }
    }

    totalDebitChanged(totalDebit: number) {
        this.totalDebit = totalDebit;
    }

    totalCreditChanged(totalCredit: number) {
        this.totalCredit = totalCredit;
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
