import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPi } from 'app/shared/model/commercial-pi.model';
import { CommercialPiService } from './commercial-pi.service';

@Component({
    selector: 'jhi-commercial-pi-delete-dialog',
    templateUrl: './commercial-pi-delete-dialog.component.html'
})
export class CommercialPiDeleteDialogComponent {
    commercialPi: ICommercialPi;

    constructor(
        protected commercialPiService: CommercialPiService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialPiService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialPiListModification',
                content: 'Deleted an commercialPi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-pi-delete-popup',
    template: ''
})
export class CommercialPiDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPi }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPiDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialPi = commercialPi;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-pi', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-pi', { outlets: { popup: null } }]);
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
