import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockInProcess } from 'app/shared/model/stock-in-process.model';
import { StockInProcessService } from './stock-in-process.service';

@Component({
    selector: 'jhi-stock-in-process-delete-dialog',
    templateUrl: './stock-in-process-delete-dialog.component.html'
})
export class StockInProcessDeleteDialogComponent {
    stockInProcess: IStockInProcess;

    constructor(
        protected stockInProcessService: StockInProcessService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-stock-in-process-delete-popup',
    template: ''
})
export class StockInProcessDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInProcess }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockInProcessDeleteDialogComponent as Component, {
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
