import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IContraVoucher } from 'app/shared/model/contra-voucher.model';
import { CurrencyFlag, ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';
import { ContraVoucherExtendedService } from 'app/entities/contra-voucher-extended/contra-voucher-extended.service';
import { ContraVoucherUpdateComponent } from 'app/entities/contra-voucher';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { ConversionFactorExtendedService } from 'app/entities/conversion-factor-extended';
import { DtTransactionExtendedService } from 'app/entities/dt-transaction-extended';

@Component({
    selector: 'jhi-contra-voucher-update',
    templateUrl: './contra-voucher-extended-update.component.html'
})
export class ContraVoucherExtendedUpdateComponent extends ContraVoucherUpdateComponent implements OnInit {
    totalCredit: number;
    totalDebit: number;
    conversionFactor: IConversionFactor;
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected contraVoucherService: ContraVoucherExtendedService,
        protected currencyService: CurrencyService,
        protected activatedRoute: ActivatedRoute,
        protected conversionFactorService: ConversionFactorExtendedService,
        protected dtTransactionService: DtTransactionExtendedService
    ) {
        super(jhiAlertService, contraVoucherService, currencyService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.totalDebit = 0;
        this.totalCredit = 0;
        this.activatedRoute.data.subscribe(({ contraVoucher }) => {
            this.contraVoucher = contraVoucher;
            this.loadAll();
        });
    }

    loadAll() {
        this.currencyService
            .query({
                size: 60
            })
            .subscribe((response: HttpResponse<ICurrency[]>) => {
                this.currencies = response.body;
                if (this.contraVoucher.currencyId === undefined) {
                    this.currencies.forEach((c: ICurrency) => {
                        if (c.flag == CurrencyFlag.BASE) {
                            this.contraVoucher.currencyId = c.id;
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
                'currencyId.equals': this.contraVoucher.currencyId,
                sort: ['modifiedOn,desc']
            })
            .subscribe((response: HttpResponse<IConversionFactor[]>) => {
                this.contraVoucher.conversionFactor = response.body[0].bcovFactor;
            });
    }

    totalDebitChanged(totalDebit: number) {
        this.totalDebit = totalDebit;
    }

    totalCreditChanged(totalCredit: number) {
        this.totalCredit = totalCredit;
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContraVoucher>>) {
        result.subscribe(
            (res: HttpResponse<IContraVoucher>) => {
                this.onSaveSuccess();
                this.contraVoucher = res.body;
            },
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
    }

    public post() {
        this.contraVoucher.postDate = moment();
        this.save();
    }

    downloadVoucherReport() {
        this.dtTransactionService.downloadVoucherReport('CONTRA VOUCHER', this.contraVoucher.voucherNo, this.contraVoucher.voucherDate);
    }
}
