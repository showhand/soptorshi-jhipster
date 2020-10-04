import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { StockInItemExtendedService } from './stock-in-item-extended.service';
import { StockInItemDeleteDialogComponent, StockInItemDeletePopupComponent } from 'app/entities/stock-in-item';

@Component({
    selector: 'jhi-stock-in-item-delete-dialog-extended',
    templateUrl: './stock-in-item-delete-dialog-extended.component.html'
})
export class StockInItemDeleteDialogExtendedComponent extends StockInItemDeleteDialogComponent {
    constructor(
        protected stockInItemService: StockInItemExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(stockInItemService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-stock-in-item-delete-popup-extended',
    template: ''
})
export class StockInItemDeletePopupExtendedComponent extends StockInItemDeletePopupComponent implements OnInit {
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
}
