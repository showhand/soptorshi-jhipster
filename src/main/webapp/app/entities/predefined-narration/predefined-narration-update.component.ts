import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPredefinedNarration } from 'app/shared/model/predefined-narration.model';
import { PredefinedNarrationService } from './predefined-narration.service';
import { IVoucher } from 'app/shared/model/voucher.model';
import { VoucherService } from 'app/entities/voucher';

@Component({
    selector: 'jhi-predefined-narration-update',
    templateUrl: './predefined-narration-update.component.html'
})
export class PredefinedNarrationUpdateComponent implements OnInit {
    predefinedNarration: IPredefinedNarration;
    isSaving: boolean;

    vouchers: IVoucher[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected predefinedNarrationService: PredefinedNarrationService,
        protected voucherService: VoucherService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ predefinedNarration }) => {
            this.predefinedNarration = predefinedNarration;
        });
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
        if (this.predefinedNarration.id !== undefined) {
            this.subscribeToSaveResponse(this.predefinedNarrationService.update(this.predefinedNarration));
        } else {
            this.subscribeToSaveResponse(this.predefinedNarrationService.create(this.predefinedNarration));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPredefinedNarration>>) {
        result.subscribe((res: HttpResponse<IPredefinedNarration>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVoucherById(index: number, item: IVoucher) {
        return item.id;
    }
}
