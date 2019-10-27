import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Moment } from 'moment';
import { JournalVoucherExtendedService } from './journal-voucher-extended.service';
import { JournalVoucherUpdateComponent } from 'app/entities/journal-voucher';
import { CurrencyFlag, ICurrency } from 'app/shared/model/currency.model';
import { VoucherType } from 'app/shared/model/dt-transaction.model';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { CurrencyExtendedService } from 'app/entities/currency-extended';
import { ConversionFactorExtendedService } from 'app/entities/conversion-factor-extended';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-journal-voucher-update',
    templateUrl: './journal-voucher-extended-update.component.html'
})
export class JournalVoucherExtendedUpdateComponent extends JournalVoucherUpdateComponent implements OnInit {
    voucherNo: string;
    voucherDate: Moment;
    selectedCurrency: ICurrency;
    type: VoucherType;
    currencies: ICurrency[];
    conversionFactor: IConversionFactor;
    totalDebit: number;
    totalCredit: number;

    constructor(
        protected journalVoucherService: JournalVoucherExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected currencyExtendedService: CurrencyExtendedService,
        protected conversionFactorExtendedService: ConversionFactorExtendedService,
        protected jhiAlertService: JhiAlertService
    ) {
        super(journalVoucherService, activatedRoute);
    }

    loadAll() {
        this.currencyExtendedService
            .query({
                size: 50
            })
            .subscribe(
                (response: HttpResponse<ICurrency[]>) => {
                    this.currencies = response.body;
                    this.currencies.forEach((c: ICurrency) => {
                        if (c.flag === CurrencyFlag.BASE) {
                            console.log('found base');
                            this.selectedCurrency = c;
                            this.fetchConversionFactorForSelectedCurrency();
                        }
                    });
                },
                (error: HttpErrorResponse) => {
                    this.jhiAlertService.error('Error in fetching currency data');
                }
            );
    }

    fetchConversionFactorForSelectedCurrency() {
        this.conversionFactorExtendedService
            .query({
                'currencyId.equals': this.selectedCurrency.id,
                sort: ['modifiedOn,desc']
            })
            .subscribe((response: HttpResponse<IConversionFactor[]>) => {
                this.conversionFactor = response.body[0];
            });
    }

    ngOnInit() {
        this.loadAll();
        super.ngOnInit();
    }
}
