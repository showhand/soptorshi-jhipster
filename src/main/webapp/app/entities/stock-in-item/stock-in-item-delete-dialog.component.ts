import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { StockInItemService } from './stock-in-item.service';

@Component({
    selector: 'jhi-stock-in-item-delete-dialog',
    templateUrl: './stock-in-item-delete-dialog.component.html'
})
export class StockInItemDeleteDialogComponent {
    stockInItem: IStockInItem;

    constructor(
        protected stockInItemService: StockInItemService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-stock-in-item-delete-popup',
    template: ''
})
export class StockInItemDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockInItemDeleteDialogComponent as Component, {
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
