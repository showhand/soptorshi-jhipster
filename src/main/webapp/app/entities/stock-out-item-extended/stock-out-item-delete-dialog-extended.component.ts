import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StockOutItemExtendedService } from './stock-out-item-extended.service';
import { StockOutItemDeleteDialogComponent, StockOutItemDeletePopupComponent } from 'app/entities/stock-out-item';

@Component({
    selector: 'jhi-stock-out-item-delete-dialog-extended',
    templateUrl: './stock-out-item-delete-dialog-extended.component.html'
})
export class StockOutItemDeleteDialogExtendedComponent extends StockOutItemDeleteDialogComponent {
    constructor(
        protected stockOutItemService: StockOutItemExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(stockOutItemService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-stock-out-item-delete-popup-extended',
    template: ''
})
export class StockOutItemDeletePopupExtendedComponent extends StockOutItemDeletePopupComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockOutItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockOutItemDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.stockOutItem = stockOutItem;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/stock-out-item', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/stock-out-item', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }
}
