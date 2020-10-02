import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';
import { CommercialPaymentInfoService } from './commercial-payment-info.service';
import { ICommercialPi } from 'app/shared/model/commercial-pi.model';
import { CommercialPiService } from 'app/entities/commercial-pi';

@Component({
    selector: 'jhi-commercial-payment-info-update',
    templateUrl: './commercial-payment-info-update.component.html'
})
export class CommercialPaymentInfoUpdateComponent implements OnInit {
    commercialPaymentInfo: ICommercialPaymentInfo;
    isSaving: boolean;

    commercialpis: ICommercialPi[];
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commercialPaymentInfoService: CommercialPaymentInfoService,
        protected commercialPiService: CommercialPiService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialPaymentInfo }) => {
            this.commercialPaymentInfo = commercialPaymentInfo;
            this.createdOn =
                this.commercialPaymentInfo.createdOn != null ? this.commercialPaymentInfo.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn =
                this.commercialPaymentInfo.updatedOn != null ? this.commercialPaymentInfo.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.commercialPiService
            .query({ 'commercialPaymentInfoId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPi[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPi[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPi[]) => {
                    if (!this.commercialPaymentInfo.commercialPiId) {
                        this.commercialpis = res;
                    } else {
                        this.commercialPiService
                            .find(this.commercialPaymentInfo.commercialPiId)
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
        this.commercialPaymentInfo.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.commercialPaymentInfo.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.commercialPaymentInfo.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialPaymentInfoService.update(this.commercialPaymentInfo));
        } else {
            this.subscribeToSaveResponse(this.commercialPaymentInfoService.create(this.commercialPaymentInfo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialPaymentInfo>>) {
        result.subscribe(
            (res: HttpResponse<ICommercialPaymentInfo>) => this.onSaveSuccess(),
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

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCommercialPiById(index: number, item: ICommercialPi) {
        return item.id;
    }
}
