import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICommercialAttachment } from 'app/shared/model/commercial-attachment.model';
import { CommercialAttachmentDetailComponent } from 'app/entities/commercial-attachment';

@Component({
    selector: 'jhi-commercial-attachment-detail-extended',
    templateUrl: './commercial-attachment-detail-extended.component.html'
})
export class CommercialAttachmentDetailExtendedComponent extends CommercialAttachmentDetailComponent {
    commercialAttachment: ICommercialAttachment;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }

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
