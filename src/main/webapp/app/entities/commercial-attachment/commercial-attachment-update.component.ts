import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ICommercialAttachment } from 'app/shared/model/commercial-attachment.model';
import { CommercialAttachmentService } from './commercial-attachment.service';
import { ICommercialPo } from 'app/shared/model/commercial-po.model';
import { CommercialPoService } from 'app/entities/commercial-po';

@Component({
    selector: 'jhi-commercial-attachment-update',
    templateUrl: './commercial-attachment-update.component.html'
})
export class CommercialAttachmentUpdateComponent implements OnInit {
    commercialAttachment: ICommercialAttachment;
    isSaving: boolean;

    commercialpos: ICommercialPo[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected commercialAttachmentService: CommercialAttachmentService,
        protected commercialPoService: CommercialPoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commercialAttachment }) => {
            this.commercialAttachment = commercialAttachment;
        });
        this.commercialPoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommercialPo[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommercialPo[]>) => response.body)
            )
            .subscribe((res: ICommercialPo[]) => (this.commercialpos = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    trackCommercialPoById(index: number, item: ICommercialPo) {
        return item.id;
    }
}
