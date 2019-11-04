import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICommercialAttachment } from 'app/shared/model/commercial-attachment.model';

@Component({
    selector: 'jhi-commercial-attachment-detail',
    templateUrl: './commercial-attachment-detail.component.html'
})
export class CommercialAttachmentDetailComponent implements OnInit {
    commercialAttachment: ICommercialAttachment;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialAttachment }) => {
            this.commercialAttachment = commercialAttachment;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
