import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockStatus } from 'app/shared/model/stock-status.model';
import { StockStatusExtendedService } from './stock-status-extended.service';
import { StockStatusDeleteDialogComponent, StockStatusDeletePopupComponent } from 'app/entities/stock-status';

@Component({
    selector: 'jhi-stock-status-delete-dialog-extended',
    templateUrl: './stock-status-delete-dialog-extended.component.html'
})
export class StockStatusDeleteDialogExtendedComponent extends StockStatusDeleteDialogComponent {
    stockStatus: IStockStatus;

    constructor(
        protected stockStatusService: StockStatusExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(stockStatusService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stockStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'stockStatusListModification',
                content: 'Deleted an stockStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stock-status-delete-popup-extended',
    template: ''
})
export class StockStatusDeletePopupExtendedComponent extends StockStatusDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockStatusDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.stockStatus = stockStatus;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/stock-status', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/stock-status', { outlets: { popup: null } }]);
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
