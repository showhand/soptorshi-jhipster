import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';
import { VoucherNumberControlService } from './voucher-number-control.service';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';

@Component({
    selector: 'jhi-voucher-number-control-update',
    templateUrl: './voucher-number-control-update.component.html'
})
export class VoucherNumberControlUpdateComponent implements OnInit {
    voucherNumberControl: IVoucherNumberControl;
    isSaving: boolean;

    financialaccountyears: IFinancialAccountYear[];

    vouchers: IVoucher[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected voucherNumberControlService: VoucherNumberControlService,
        protected financialAccountYearService: FinancialAccountYearService,
        protected voucherService: VoucherService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ voucherNumberControl }) => {
            this.voucherNumberControl = voucherNumberControl;
        });
        this.financialAccountYearService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFinancialAccountYear[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFinancialAccountYear[]>) => response.body)
            )
            .subscribe(
                (res: IFinancialAccountYear[]) => (this.financialaccountyears = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.voucherService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVoucher[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVoucher[]>) => response.body)
            )
            .subscribe((res: IVoucher[]) => (this.vouchers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.voucherNumberControl.id !== undefined) {
            this.subscribeToSaveResponse(this.voucherNumberControlService.update(this.voucherNumberControl));
        } else {
            this.subscribeToSaveResponse(this.voucherNumberControlService.create(this.voucherNumberControl));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucherNumberControl>>) {
        result.subscribe(
            (res: HttpResponse<IVoucherNumberControl>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackFinancialAccountYearById(index: number, item: IFinancialAccountYear) {
        return item.id;
    }

    trackVoucherById(index: number, item: IVoucher) {
        return item.id;
    }
}
