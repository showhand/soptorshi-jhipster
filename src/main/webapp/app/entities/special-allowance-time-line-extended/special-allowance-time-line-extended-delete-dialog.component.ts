import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';
import { SpecialAllowanceTimeLineExtendedService } from './special-allowance-time-line-extended.service';
import {
    SpecialAllowanceTimeLineDeleteDialogComponent,
    SpecialAllowanceTimeLineDeletePopupComponent
} from 'app/entities/special-allowance-time-line';

@Component({
    selector: 'jhi-special-allowance-time-line-delete-dialog',
    templateUrl: './special-allowance-time-line-extended-delete-dialog.component.html'
})
export class SpecialAllowanceTimeLineExtendedDeleteDialogComponent extends SpecialAllowanceTimeLineDeleteDialogComponent {
    specialAllowanceTimeLine: ISpecialAllowanceTimeLine;

    constructor(
        protected specialAllowanceTimeLineService: SpecialAllowanceTimeLineExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(specialAllowanceTimeLineService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-special-allowance-time-line-delete-popup',
    template: ''
})
export class SpecialAllowanceTimeLineExtendedDeletePopupComponent extends SpecialAllowanceTimeLineDeletePopupComponent
    implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ specialAllowanceTimeLine }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SpecialAllowanceTimeLineExtendedDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.specialAllowanceTimeLine = specialAllowanceTimeLine;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/special-allowance-time-line', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/special-allowance-time-line', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }
}
