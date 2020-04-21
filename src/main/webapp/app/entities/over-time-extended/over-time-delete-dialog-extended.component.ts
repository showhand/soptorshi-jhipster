import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOverTime } from 'app/shared/model/over-time.model';
import { OverTimeExtendedService } from './over-time-extended.service';
import { OverTimeDeleteDialogComponent, OverTimeDeletePopupComponent } from 'app/entities/over-time';

@Component({
    selector: 'jhi-over-time-delete-dialog-extended',
    templateUrl: './over-time-delete-dialog-extended.component.html'
})
export class OverTimeDeleteDialogExtendedComponent extends OverTimeDeleteDialogComponent {
    overTime: IOverTime;

    constructor(
        protected overTimeService: OverTimeExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(overTimeService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-over-time-delete-popup-extended',
    template: ''
})
export class OverTimeDeletePopupExtendedComponent extends OverTimeDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
