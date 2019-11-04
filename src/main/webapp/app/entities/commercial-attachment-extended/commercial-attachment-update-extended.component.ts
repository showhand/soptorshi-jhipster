import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ICommercialAttachment } from 'app/shared/model/commercial-attachment.model';
import { CommercialAttachmentExtendedService } from './commercial-attachment-extended.service';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderService } from 'app/entities/commercial-purchase-order';
import { ICommercialPoStatus } from 'app/shared/model/commercial-po-status.model';
import { CommercialPoStatusService } from 'app/entities/commercial-po-status';
import { CommercialAttachmentUpdateComponent } from 'app/entities/commercial-attachment';

@Component({
    selector: 'jhi-commercial-attachment-update-extended',
    templateUrl: './commercial-attachment-update-extended.component.html'
})
export class CommercialAttachmentUpdateExtendedComponent extends CommercialAttachmentUpdateComponent {
    commercialAttachment: ICommercialAttachment;
    isSaving: boolean;

    commercialpurchaseorders: ICommercialPurchaseOrder[];

    commercialpostatuses: ICommercialPoStatus[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected commercialAttachmentService: CommercialAttachmentExtendedService,
        protected commercialPurchaseOrderService: CommercialPurchaseOrderService,
        protected commercialPoStatusService: CommercialPoStatusService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            dataUtils,
            jhiAlertService,
            commercialAttachmentService,
            commercialPurchaseOrderService,
            commercialPoStatusService,
            activatedRoute
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialAttachment }) => {
            this.commercialAttachment = commercialAttachment;
        });
        this.commercialPurchaseOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPurchaseOrder[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPurchaseOrder[]) => (this.commercialpurchaseorders = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.commercialPoStatusService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPoStatus[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPoStatus[]>) => response.body)
            )
            .subscribe(
                (res: ICommercialPoStatus[]) => (this.commercialpostatuses = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commercialAttachment.id !== undefined) {
            this.subscribeToSaveResponse(this.commercialAttachmentService.update(this.commercialAttachment));
        } else {
            this.subscribeToSaveResponse(this.commercialAttachmentService.create(this.commercialAttachment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommercialAttachment>>) {
        result.subscribe(
            (res: HttpResponse<ICommercialAttachment>) => this.onSaveSuccess(),
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

    trackCommercialPurchaseOrderById(index: number, item: ICommercialPurchaseOrder) {
        return item.id;
    }

    trackCommercialPoStatusById(index: number, item: ICommercialPoStatus) {
        return item.id;
    }
}
