import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { AccountService } from 'app/core';

import { CommercialAttachmentExtendedService } from './commercial-attachment-extended.service';
import { CommercialAttachmentComponent } from 'app/entities/commercial-attachment';

@Component({
    selector: 'jhi-commercial-attachment-extended',
    templateUrl: './commercial-attachment-extended.component.html'
})
export class CommercialAttachmentExtendedComponent extends CommercialAttachmentComponent {
    constructor(
        protected commercialAttachmentService: CommercialAttachmentExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(commercialAttachmentService, jhiAlertService, dataUtils, eventManager, parseLinks, activatedRoute, accountService);
    }
}
