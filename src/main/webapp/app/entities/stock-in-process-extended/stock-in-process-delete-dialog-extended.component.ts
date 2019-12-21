import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessExtendedService } from './stock-in-process-extended.service';
import { StockInProcessDeleteDialogComponent, StockInProcessDeletePopupComponent } from 'app/entities/stock-in-process';

@Component({
    selector: 'jhi-stock-in-process-delete-dialog-extended',
    templateUrl: './stock-in-process-delete-dialog-extended.component.html'
})
export class StockInProcessDeleteDialogExtendedComponent extends StockInProcessDeleteDialogComponent {
    stockInProcess: IStockInProcess;

    constructor(
        protected stockInProcessService: StockInProcessExtendedService,
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
export class StockInProcessDeletePopupExtendedComponent extends StockInProcessDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
