import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMontlySalaryVouchers } from 'app/shared/model/montly-salary-vouchers.model';
import { MontlySalaryVouchersService } from './montly-salary-vouchers.service';

@Component({
    selector: 'jhi-montly-salary-vouchers-update',
    templateUrl: './montly-salary-vouchers-update.component.html'
})
export class MontlySalaryVouchersUpdateComponent implements OnInit {
    montlySalaryVouchers: IMontlySalaryVouchers;
    isSaving: boolean;

    constructor(protected montlySalaryVouchersService: MontlySalaryVouchersService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ montlySalaryVouchers }) => {
            this.montlySalaryVouchers = montlySalaryVouchers;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.montlySalaryVouchers.id !== undefined) {
            this.subscribeToSaveResponse(this.montlySalaryVouchersService.update(this.montlySalaryVouchers));
        } else {
            this.subscribeToSaveResponse(this.montlySalaryVouchersService.create(this.montlySalaryVouchers));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMontlySalaryVouchers>>) {
        result.subscribe(
            (res: HttpResponse<IMontlySalaryVouchers>) => this.onSaveSuccess(),
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
}
