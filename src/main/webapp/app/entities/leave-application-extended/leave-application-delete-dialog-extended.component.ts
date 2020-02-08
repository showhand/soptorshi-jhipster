import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { LeaveApplicationExtendedService } from './leave-application-extended.service';
import { LeaveApplicationDeleteDialogComponent, LeaveApplicationDeletePopupComponent } from 'app/entities/leave-application';

@Component({
    selector: 'jhi-leave-application-delete-dialog-extended',
    templateUrl: './leave-application-delete-dialog-extended.component.html'
})
export class LeaveApplicationDeleteDialogExtendedComponent extends LeaveApplicationDeleteDialogComponent {
    constructor(
        protected leaveApplicationService: LeaveApplicationExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(leaveApplicationService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-leave-application-delete-popup-extended',
    template: ''
})
export class LeaveApplicationDeletePopupExtendedComponent extends LeaveApplicationDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
