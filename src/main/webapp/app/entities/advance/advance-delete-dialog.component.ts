import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdvance } from 'app/shared/model/advance.model';
import { AdvanceService } from './advance.service';

@Component({
    selector: 'jhi-advance-delete-dialog',
    templateUrl: './advance-delete-dialog.component.html'
})
export class AdvanceDeleteDialogComponent {
    advance: IAdvance;

    constructor(protected advanceService: AdvanceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.advanceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'advanceListModification',
                content: 'Deleted an advance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-advance-delete-popup',
    template: ''
})
export class AdvanceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ advance }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdvanceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.advance = advance;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/advance', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/advance', { outlets: { popup: null } }]);
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
