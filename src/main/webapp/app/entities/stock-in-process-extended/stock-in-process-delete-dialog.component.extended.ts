import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessServiceExtended } from './stock-in-process.service.extended';
import { StockInProcessDeleteDialogComponent, StockInProcessDeletePopupComponent } from 'app/entities/stock-in-process';

@Component({
    selector: 'jhi-stock-in-process-delete-dialog-extended',
    templateUrl: './stock-in-process-delete-dialog.component.extended.html'
})
export class StockInProcessDeleteDialogComponentExtended extends StockInProcessDeleteDialogComponent {
    stockInProcess: IStockInProcess;

    constructor(
        protected stockInProcessService: StockInProcessServiceExtended,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(stockInProcessService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stockInProcessService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'stockInProcessListModification',
                content: 'Deleted an stockInProcess'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stock-in-process-delete-popup-extended',
    template: ''
})
export class StockInProcessDeletePopupComponentExtended extends StockInProcessDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInProcess }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockInProcessDeleteDialogComponentExtended as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.stockInProcess = stockInProcess;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/stock-in-process', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/stock-in-process', { outlets: { popup: null } }]);
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
