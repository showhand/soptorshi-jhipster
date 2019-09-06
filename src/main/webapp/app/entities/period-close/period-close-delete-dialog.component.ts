import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPeriodClose } from 'app/shared/model/period-close.model';
import { PeriodCloseService } from './period-close.service';

@Component({
    selector: 'jhi-period-close-delete-dialog',
    templateUrl: './period-close-delete-dialog.component.html'
})
export class PeriodCloseDeleteDialogComponent {
    periodClose: IPeriodClose;

    constructor(
        protected periodCloseService: PeriodCloseService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.periodCloseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'periodCloseListModification',
                content: 'Deleted an periodClose'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-period-close-delete-popup',
    template: ''
})
export class PeriodCloseDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ periodClose }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PeriodCloseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.periodClose = periodClose;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/period-close', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/period-close', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
