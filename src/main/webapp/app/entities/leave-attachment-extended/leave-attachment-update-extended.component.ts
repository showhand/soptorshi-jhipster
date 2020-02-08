import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { LeaveAttachmentExtendedService } from './leave-attachment-extended.service';
import { LeaveApplicationService } from 'app/entities/leave-application';
import { LeaveAttachmentUpdateComponent } from 'app/entities/leave-attachment';

@Component({
    selector: 'jhi-leave-attachment-update-extended',
    templateUrl: './leave-attachment-update-extended.component.html'
})
export class LeaveAttachmentUpdateExtendedComponent extends LeaveAttachmentUpdateComponent {
    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected leaveAttachmentService: LeaveAttachmentExtendedService,
        protected leaveApplicationService: LeaveApplicationService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router
    ) {
        super(dataUtils, jhiAlertService, leaveAttachmentService, leaveApplicationService, activatedRoute);
    }
}
