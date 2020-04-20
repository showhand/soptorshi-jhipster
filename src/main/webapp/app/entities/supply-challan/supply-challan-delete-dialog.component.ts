import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyChallan } from 'app/shared/model/supply-challan.model';
import { SupplyChallanService } from './supply-challan.service';

@Component({
    selector: 'jhi-supply-challan-delete-dialog',
    templateUrl: './supply-challan-delete-dialog.component.html'
})
export class SupplyChallanDeleteDialogComponent {
    supplyChallan: ISupplyChallan;

    constructor(
        protected supplyChallanService: SupplyChallanService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyChallanService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyChallanListModification',
                content: 'Deleted an supplyChallan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-challan-delete-popup',
    template: ''
})
export class SupplyChallanDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyChallan }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyChallanDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.supplyChallan = supplyChallan;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-challan', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-challan', { outlets: { popup: null } }]);
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
