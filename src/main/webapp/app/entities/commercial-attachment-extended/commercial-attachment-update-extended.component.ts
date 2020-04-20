import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { CommercialAttachmentService, CommercialAttachmentUpdateComponent } from 'app/entities/commercial-attachment';
import { CommercialPiService } from 'app/entities/commercial-pi';

@Component({
    selector: 'jhi-commercial-attachment-update-extended',
    templateUrl: './commercial-attachment-update-extended.component.html'
})
export class CommercialAttachmentUpdateExtendedComponent extends CommercialAttachmentUpdateComponent {
    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected commercialAttachmentService: CommercialAttachmentService,
        protected commercialPiService: CommercialPiService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(dataUtils, jhiAlertService, commercialAttachmentService, commercialPiService, activatedRoute);
    }
}
