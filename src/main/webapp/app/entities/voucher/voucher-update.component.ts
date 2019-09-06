import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from './voucher.service';

@Component({
    selector: 'jhi-voucher-update',
    templateUrl: './voucher-update.component.html'
})
export class VoucherUpdateComponent implements OnInit {
    voucher: IVoucher;
    isSaving: boolean;

    constructor(protected voucherService: VoucherService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ voucher }) => {
            this.voucher = voucher;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.voucher.id !== undefined) {
            this.subscribeToSaveResponse(this.voucherService.update(this.voucher));
        } else {
            this.subscribeToSaveResponse(this.voucherService.create(this.voucher));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IVoucher>>) {
        result.subscribe((res: HttpResponse<IVoucher>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
