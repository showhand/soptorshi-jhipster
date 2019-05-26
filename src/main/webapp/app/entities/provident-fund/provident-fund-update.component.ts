import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IProvidentFund } from 'app/shared/model/provident-fund.model';
import { ProvidentFundService } from './provident-fund.service';

@Component({
    selector: 'jhi-provident-fund-update',
    templateUrl: './provident-fund-update.component.html'
})
export class ProvidentFundUpdateComponent implements OnInit {
    providentFund: IProvidentFund;
    isSaving: boolean;
    startDateDp: any;
    modifiedOnDp: any;

    constructor(protected providentFundService: ProvidentFundService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ providentFund }) => {
            this.providentFund = providentFund;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.providentFund.id !== undefined) {
            this.subscribeToSaveResponse(this.providentFundService.update(this.providentFund));
        } else {
            this.subscribeToSaveResponse(this.providentFundService.create(this.providentFund));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvidentFund>>) {
        result.subscribe((res: HttpResponse<IProvidentFund>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
