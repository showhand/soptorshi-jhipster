import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { StockInProcessExtendedService } from './stock-in-process-extended.service';
import { StockInProcessDeleteDialogComponent, StockInProcessDeletePopupComponent } from 'app/entities/stock-in-process';

@Component({
    selector: 'jhi-stock-in-process-delete-dialog-extended',
    templateUrl: './stock-in-process-delete-dialog-extended.component.html'
})
export class StockInProcessDeleteDialogExtendedComponent extends StockInProcessDeleteDialogComponent {
    constructor(
        protected stockInProcessService: StockInProcessExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(stockInProcessService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-stock-in-process-delete-popup-extended',
    template: ''
})
export class StockInProcessDeletePopupExtendedComponent extends StockInProcessDeletePopupComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInProcess }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockInProcessDeleteDialogExtendedComponent as Component, {
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
}
