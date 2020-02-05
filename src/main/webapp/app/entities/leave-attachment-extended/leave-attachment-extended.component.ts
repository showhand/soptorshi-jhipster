import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { LeaveAttachmentExtendedService } from './leave-attachment-extended.service';
import { LeaveAttachmentComponent } from 'app/entities/leave-attachment';

@Component({
    selector: 'jhi-leave-attachment-extended',
    templateUrl: './leave-attachment-extended.component.html'
})
export class LeaveAttachmentExtendedComponent extends LeaveAttachmentComponent {
    constructor(
        protected leaveAttachmentService: LeaveAttachmentExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected dataUtils: JhiDataUtils,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(leaveAttachmentService, jhiAlertService, dataUtils, eventManager, parseLinks, activatedRoute, accountService);
    }
}
