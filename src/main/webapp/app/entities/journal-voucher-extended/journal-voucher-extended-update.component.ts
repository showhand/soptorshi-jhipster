import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApplicationType, IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherUpdateComponent } from 'app/entities/journal-voucher';
import { CurrencyFlag, ICurrency } from 'app/shared/model/currency.model';
import { JournalVoucherExtendedService } from 'app/entities/journal-voucher-extended/journal-voucher-extended.service';
import { HttpResponse } from '@angular/common/http';
import { CurrencyExtendedService } from 'app/entities/currency-extended';
import { ConversionFactorExtendedService } from 'app/entities/conversion-factor-extended';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { VoucherType } from 'app/shared/model/dt-transaction.model';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import * as moment from 'moment';
import { DtTransactionExtendedService } from 'app/entities/dt-transaction-extended';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { forkJoin } from 'rxjs';

@Component({
    selector: 'jhi-journal-voucher-extended-update',
    templateUrl: './journal-voucher-extended-update.component.html'
})
export class JournalVoucherExtendedUpdateComponent extends JournalVoucherUpdateComponent implements OnInit {
    totalDebit: number;
    totalCredit: number;
    currencies: ICurrency[];
    selectedCurrency: ICurrency;
    conversionFactor: IConversionFactor;
    isSaving: boolean;
    voucherType: VoucherType;
    journalVoucherId: number;
    purchaseOrder: IPurchaseOrder;
    purchaseOrders: IPurchaseOrder[];
    requisition: IRequisition;

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected journalVoucherService: JournalVoucherExtendedService,
        protected currencyService: CurrencyExtendedService,
        protected conversionFactorService: ConversionFactorExtendedService,
        protected eventManager: JhiEventManager,
        protected jhiAlertService: JhiAlertService,
        protected router: Router,
        protected dtTransactionService: DtTransactionExtendedService,
        protected purchaseOrderService: PurchaseOrderService,
        protected requisitionService: RequisitionService
    ) {
        super(jhiAlertService, journalVoucherService, currencyService, activatedRoute);
        this.totalCredit = 0;
        this.totalDebit = 0;
        this.isSaving = true;
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ journalVoucher }) => {
            this.journalVoucher = journalVoucher;
            if (this.journalVoucher.applicationType === ApplicationType.REQUISITION) {
                forkJoin(
                    this.requisitionService.find(this.journalVoucher.applicationId),
                    this.purchaseOrderService.query({
                        'requisitionId.equals': this.journalVoucher.applicationId
                    })
                ).subscribe(res => {
                    this.requisition = res[0].body;
                    this.purchaseOrders = res[1].body;
                });
            }
            this.loadAll();
            //this.journalVoucher.type = this.voucherType == null ? (this.voucherType = VoucherType.BUYING) : this.voucherType;
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
                if (this.journalVoucher.currencyId === undefined) {
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

        if (this.journalVoucher.id !== undefined) {
            this.journalVoucherService.update(this.journalVoucher).subscribe((response: HttpResponse<IJournalVoucher>) => {
                this.journalVoucher = response.body;
            });
        } else {
            this.journalVoucherService.create(this.journalVoucher).subscribe((response: HttpResponse<IJournalVoucher>) => {
                this.journalVoucher = response.body;
            });
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

    downloadVoucherReport() {
        this.dtTransactionService.downloadVoucherReport('JOURNAL VOUCHER', this.journalVoucher.voucherNo, this.journalVoucher.voucherDate);
    }
}
