import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IChequeRegister } from 'app/shared/model/cheque-register.model';
import { ChequeRegisterService } from './cheque-register.service';

@Component({
    selector: 'jhi-cheque-register-update',
    templateUrl: './cheque-register-update.component.html'
})
export class ChequeRegisterUpdateComponent implements OnInit {
    chequeRegister: IChequeRegister;
    isSaving: boolean;
    chequeDateDp: any;
    realizationDateDp: any;
    modifiedOnDp: any;

    constructor(protected chequeRegisterService: ChequeRegisterService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chequeRegister }) => {
            this.chequeRegister = chequeRegister;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.chequeRegister.id !== undefined) {
            this.subscribeToSaveResponse(this.chequeRegisterService.update(this.chequeRegister));
        } else {
            this.subscribeToSaveResponse(this.chequeRegisterService.create(this.chequeRegister));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IChequeRegister>>) {
        result.subscribe((res: HttpResponse<IChequeRegister>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
