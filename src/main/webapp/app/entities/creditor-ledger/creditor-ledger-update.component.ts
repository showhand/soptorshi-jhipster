import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICreditorLedger } from 'app/shared/model/creditor-ledger.model';
import { CreditorLedgerService } from './creditor-ledger.service';
import { IVendor } from 'app/shared/model/vendor.model';
import { VendorService } from 'app/entities/vendor';

@Component({
    selector: 'jhi-creditor-ledger-update',
    templateUrl: './creditor-ledger-update.component.html'
})
export class CreditorLedgerUpdateComponent implements OnInit {
    creditorLedger: ICreditorLedger;
    isSaving: boolean;

    vendors: IVendor[];
    billDateDp: any;
    dueDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected creditorLedgerService: CreditorLedgerService,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ creditorLedger }) => {
            this.creditorLedger = creditorLedger;
        });
        this.vendorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IVendor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IVendor[]>) => response.body)
            )
            .subscribe((res: IVendor[]) => (this.vendors = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.creditorLedger.id !== undefined) {
            this.subscribeToSaveResponse(this.creditorLedgerService.update(this.creditorLedger));
        } else {
            this.subscribeToSaveResponse(this.creditorLedgerService.create(this.creditorLedger));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICreditorLedger>>) {
        result.subscribe((res: HttpResponse<ICreditorLedger>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVendorById(index: number, item: IVendor) {
        return item.id;
    }
}
