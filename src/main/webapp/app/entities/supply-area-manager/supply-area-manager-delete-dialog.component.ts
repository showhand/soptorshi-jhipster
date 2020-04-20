import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyAreaManagerService } from './supply-area-manager.service';

@Component({
    selector: 'jhi-supply-area-manager-delete-dialog',
    templateUrl: './supply-area-manager-delete-dialog.component.html'
})
export class SupplyAreaManagerDeleteDialogComponent {
    supplyAreaManager: ISupplyAreaManager;

    constructor(
        protected supplyAreaManagerService: SupplyAreaManagerService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyAreaManagerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyAreaManagerListModification',
                content: 'Deleted an supplyAreaManager'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-area-manager-delete-popup',
    template: ''
})
export class SupplyAreaManagerDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyAreaManager }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyAreaManagerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.supplyAreaManager = supplyAreaManager;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-area-manager', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-area-manager', { outlets: { popup: null } }]);
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
