import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPo } from 'app/shared/model/commercial-po.model';
import { CommercialPoService } from './commercial-po.service';

@Component({
    selector: 'jhi-commercial-po-delete-dialog',
    templateUrl: './commercial-po-delete-dialog.component.html'
})
export class CommercialPoDeleteDialogComponent {
    commercialPo: ICommercialPo;

    constructor(
        protected commercialPoService: CommercialPoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialPoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialPoListModification',
                content: 'Deleted an commercialPo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-po-delete-popup',
    template: ''
})
export class CommercialPoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialPo = commercialPo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-po', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-po', { outlets: { popup: null } }]);
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
