import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';
import { JournalVoucherService } from './journal-voucher.service';

@Component({
    selector: 'jhi-journal-voucher-update',
    templateUrl: './journal-voucher-update.component.html'
})
export class JournalVoucherUpdateComponent implements OnInit {
    journalVoucher: IJournalVoucher;
    isSaving: boolean;
    voucherDateDp: any;
    postDateDp: any;
    modifiedOnDp: any;

    constructor(protected journalVoucherService: JournalVoucherService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ journalVoucher }) => {
            this.journalVoucher = journalVoucher;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.journalVoucher.id !== undefined) {
            this.subscribeToSaveResponse(this.journalVoucherService.update(this.journalVoucher));
        } else {
            this.subscribeToSaveResponse(this.journalVoucherService.create(this.journalVoucher));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IJournalVoucher>>) {
        result.subscribe((res: HttpResponse<IJournalVoucher>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
