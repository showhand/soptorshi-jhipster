import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { CommercialAttachmentDetailComponent } from 'app/entities/commercial-attachment';

@Component({
    selector: 'jhi-commercial-attachment-detail-extended',
    templateUrl: './commercial-attachment-detail-extended.component.html'
})
export class CommercialAttachmentDetailExtendedComponent extends CommercialAttachmentDetailComponent {
    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}
