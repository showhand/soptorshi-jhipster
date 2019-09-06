import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPredefinedNarration } from 'app/shared/model/predefined-narration.model';
import { PredefinedNarrationService } from './predefined-narration.service';

@Component({
    selector: 'jhi-predefined-narration-delete-dialog',
    templateUrl: './predefined-narration-delete-dialog.component.html'
})
export class PredefinedNarrationDeleteDialogComponent {
    predefinedNarration: IPredefinedNarration;

    constructor(
        protected predefinedNarrationService: PredefinedNarrationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.predefinedNarrationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'predefinedNarrationListModification',
                content: 'Deleted an predefinedNarration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-predefined-narration-delete-popup',
    template: ''
})
export class PredefinedNarrationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ predefinedNarration }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PredefinedNarrationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.predefinedNarration = predefinedNarration;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/predefined-narration', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/predefined-narration', { outlets: { popup: null } }]);
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
