import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialPo } from 'app/shared/model/commercial-po.model';
import { CommercialPoService } from './commercial-po.service';
import { ICommercialPi } from 'app/shared/model/commercial-pi.model';
import { CommercialPiService } from 'app/entities/commercial-pi';

@Component({
    selector: 'jhi-commercial-po-update',
    templateUrl: './commercial-po-update.component.html'
})
export class CommercialPoUpdateComponent implements OnInit {
    commercialPo: ICommercialPo;
    isSaving: boolean;

    commercialpis: ICommercialPi[];
    purchaseOrderDateDp: any;
    shipmentDateDp: any;
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPoService: CommercialPoService,
        protected commercialPiService: CommercialPiService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialPo }) => {
            this.commercialPo = commercialPo;
            this.createdOn = this.commercialPo.createdOn != null ? this.commercialPo.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.commercialPo.updatedOn != null ? this.commercialPo.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.commercialPiService
            .query({ 'commercialPoId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPi[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPi[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPi[]) => {
                    if (!this.commercialPo.commercialPiId) {
                        this.commercialpis = res;
                    } else {
                        this.commercialPiService
                            .find(this.commercialPo.commercialPiId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ICommercialPi>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ICommercialPi>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ICommercialPi) => (this.commercialpis = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.commercialPo.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.commercialPo.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.commercialPo.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialPoService.update(this.commercialPo));
        } else {
            this.subscribeToSaveResponse(this.commercialPoService.create(this.commercialPo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialPo>>) {
        result.subscribe((res: HttpResponse<ICommercialPo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCommercialPiById(index: number, item: ICommercialPi) {
        return item.id;
    }
}
