import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplyMoneyCollection } from 'app/shared/model/supply-money-collection.model';
import { SupplyMoneyCollectionService } from './supply-money-collection.service';

@Component({
    selector: 'jhi-supply-money-collection-delete-dialog',
    templateUrl: './supply-money-collection-delete-dialog.component.html'
})
export class SupplyMoneyCollectionDeleteDialogComponent {
    supplyMoneyCollection: ISupplyMoneyCollection;

    constructor(
        protected supplyMoneyCollectionService: SupplyMoneyCollectionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplyMoneyCollectionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplyMoneyCollectionListModification',
                content: 'Deleted an supplyMoneyCollection'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-money-collection-delete-popup',
    template: ''
})
export class SupplyMoneyCollectionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyMoneyCollection }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplyMoneyCollectionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.supplyMoneyCollection = supplyMoneyCollection;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-money-collection', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-money-collection', { outlets: { popup: null } }]);
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
