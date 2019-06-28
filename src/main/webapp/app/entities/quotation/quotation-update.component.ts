import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationService } from './quotation.service';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';

@Component({
    selector: 'jhi-quotation-update',
    templateUrl: './quotation-update.component.html'
})
export class QuotationUpdateComponent implements OnInit {
    quotation: IQuotation;
    isSaving: boolean;

    requisitions: IRequisition[];
    modifiedOnDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected quotationService: QuotationService,
        protected requisitionService: RequisitionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ quotation }) => {
            this.quotation = quotation;
        });
        this.requisitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisition[]>) => response.body)
            )
            .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.quotation.id !== undefined) {
            this.subscribeToSaveResponse(this.quotationService.update(this.quotation));
        } else {
            this.subscribeToSaveResponse(this.quotationService.create(this.quotation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuotation>>) {
        result.subscribe((res: HttpResponse<IQuotation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRequisitionById(index: number, item: IRequisition) {
        return item.id;
    }
}
