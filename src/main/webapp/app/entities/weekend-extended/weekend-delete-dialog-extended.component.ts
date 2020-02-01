import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWeekend } from 'app/shared/model/weekend.model';
import { WeekendExtendedService } from 'app/entities/weekend-extended/weekend-extended.service';
import { WeekendDeleteDialogComponent, WeekendDeletePopupComponent } from 'app/entities/weekend';

@Component({
    selector: 'jhi-weekend-delete-dialog-extended',
    templateUrl: './weekend-delete-dialog-extended.component.html'
})
export class WeekendDeleteDialogExtendedComponent extends WeekendDeleteDialogComponent {
    weekend: IWeekend;

    constructor(
        protected weekendService: WeekendExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(weekendService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-weekend-delete-popup-extended',
    template: ''
})
export class WeekendDeletePopupExtendedComponent extends WeekendDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
