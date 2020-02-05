import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { LeaveAttachmentDetailComponent } from 'app/entities/leave-attachment';

@Component({
    selector: 'jhi-leave-attachment-detail-extended',
    templateUrl: './leave-attachment-detail-extended.component.html'
})
export class LeaveAttachmentDetailExtendedComponent extends LeaveAttachmentDetailComponent {
    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}
