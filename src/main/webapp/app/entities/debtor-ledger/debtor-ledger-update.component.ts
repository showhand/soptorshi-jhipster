import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IDebtorLedger } from 'app/shared/model/debtor-ledger.model';
import { DebtorLedgerService } from './debtor-ledger.service';
import { ICustomer } from 'app/shared/model/customer.model';
import { CustomerService } from 'app/entities/customer';

@Component({
    selector: 'jhi-debtor-ledger-update',
    templateUrl: './debtor-ledger-update.component.html'
})
export class DebtorLedgerUpdateComponent implements OnInit {
    debtorLedger: IDebtorLedger;
    isSaving: boolean;

    customers: ICustomer[];
    billDateDp: any;
    dueDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected debtorLedgerService: DebtorLedgerService,
        protected customerService: CustomerService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ debtorLedger }) => {
            this.debtorLedger = debtorLedger;
        });
        this.customerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICustomer[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICustomer[]>) => response.body)
            )
            .subscribe((res: ICustomer[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.debtorLedger.id !== undefined) {
            this.subscribeToSaveResponse(this.debtorLedgerService.update(this.debtorLedger));
        } else {
            this.subscribeToSaveResponse(this.debtorLedgerService.create(this.debtorLedger));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDebtorLedger>>) {
        result.subscribe((res: HttpResponse<IDebtorLedger>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCustomerById(index: number, item: ICustomer) {
        return item.id;
    }
}
