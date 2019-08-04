import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockStatus } from 'app/shared/model/stock-status.model';
import { StockStatusService } from './stock-status.service';

@Component({
    selector: 'jhi-stock-status-delete-dialog',
    templateUrl: './stock-status-delete-dialog.component.html'
})
export class StockStatusDeleteDialogComponent {
    stockStatus: IStockStatus;

    constructor(
        protected stockStatusService: StockStatusService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-stock-status-delete-popup',
    template: ''
})
export class StockStatusDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockStatusDeleteDialogComponent as Component, {
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
