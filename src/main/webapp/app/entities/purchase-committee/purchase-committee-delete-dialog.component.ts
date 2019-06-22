import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseCommittee } from 'app/shared/model/purchase-committee.model';
import { PurchaseCommitteeService } from './purchase-committee.service';

@Component({
    selector: 'jhi-purchase-committee-delete-dialog',
    templateUrl: './purchase-committee-delete-dialog.component.html'
})
export class PurchaseCommitteeDeleteDialogComponent {
    purchaseCommittee: IPurchaseCommittee;

    constructor(
        protected purchaseCommitteeService: PurchaseCommitteeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.purchaseCommitteeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'purchaseCommitteeListModification',
                content: 'Deleted an purchaseCommittee'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-purchase-committee-delete-popup',
    template: ''
})
export class PurchaseCommitteeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ purchaseCommittee }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PurchaseCommitteeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.purchaseCommittee = purchaseCommittee;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/purchase-committee', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/purchase-committee', { outlets: { popup: null } }]);
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
