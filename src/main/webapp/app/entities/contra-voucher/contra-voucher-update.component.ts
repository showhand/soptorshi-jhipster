import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IContraVoucher } from 'app/shared/model/contra-voucher.model';
import { ContraVoucherService } from './contra-voucher.service';

@Component({
    selector: 'jhi-contra-voucher-update',
    templateUrl: './contra-voucher-update.component.html'
})
export class ContraVoucherUpdateComponent implements OnInit {
    contraVoucher: IContraVoucher;
    isSaving: boolean;
    voucherDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;

    constructor(protected contraVoucherService: ContraVoucherService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contraVoucher }) => {
            this.contraVoucher = contraVoucher;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.contraVoucher.id !== undefined) {
            this.subscribeToSaveResponse(this.contraVoucherService.update(this.contraVoucher));
        } else {
            this.subscribeToSaveResponse(this.contraVoucherService.create(this.contraVoucher));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IContraVoucher>>) {
        result.subscribe((res: HttpResponse<IContraVoucher>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
