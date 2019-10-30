import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { StockInItemExtendedService } from './stock-in-item-extended.service';
import { StockInItemDeleteDialogComponent, StockInItemDeletePopupComponent } from 'app/entities/stock-in-item';

@Component({
    selector: 'jhi-stock-in-item-delete-dialog-extended',
    templateUrl: './stock-in-item-delete-dialog-extended.component.html'
})
export class StockInItemDeleteDialogExtendedComponent extends StockInItemDeleteDialogComponent {
    stockInItem: IStockInItem;

    constructor(
        protected stockInItemService: StockInItemExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(stockInItemService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stockInItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'stockInItemListModification',
                content: 'Deleted an stockInItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stock-in-item-delete-popup-extended',
    template: ''
})
export class StockInItemDeletePopupComponentExtended extends StockInItemDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockInItemDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.stockInItem = stockInItem;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/stock-in-item', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/stock-in-item', { outlets: { popup: null } }]);
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
