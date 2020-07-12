import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { SupplyAreaService } from './supply-area.service';

@Component({
    selector: 'jhi-supply-area-delete-dialog',
    templateUrl: './supply-area-delete-dialog.component.html'
})
export class SupplyAreaDeleteDialogComponent {
    supplyArea: ISupplyArea;

    constructor(
        protected supplyAreaService: SupplyAreaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyAreaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyAreaListModification',
                content: 'Deleted an supplyArea'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-area-delete-popup',
    template: ''
})
export class SupplyAreaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyArea }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyAreaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.supplyArea = supplyArea;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-area', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-area', { outlets: { popup: null } }]);
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
