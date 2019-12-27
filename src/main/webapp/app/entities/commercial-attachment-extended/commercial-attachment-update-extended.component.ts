import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { CommercialAttachmentExtendedService } from './commercial-attachment-extended.service';
import { CommercialPoService } from 'app/entities/commercial-po';
import { CommercialAttachmentUpdateComponent } from 'app/entities/commercial-attachment';

@Component({
    selector: 'jhi-commercial-attachment-update-extended',
    templateUrl: './commercial-attachment-update-extended.component.html'
})
export class CommercialAttachmentUpdateExtendedComponent extends CommercialAttachmentUpdateComponent {
    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected commercialAttachmentService: CommercialAttachmentExtendedService,
        protected commercialPoService: CommercialPoService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(dataUtils, jhiAlertService, commercialAttachmentService, commercialPoService, activatedRoute);
    }
}
