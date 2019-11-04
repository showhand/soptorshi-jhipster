import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialInvoice } from 'app/shared/model/commercial-invoice.model';
import { CommercialInvoiceService } from './commercial-invoice.service';

@Component({
    selector: 'jhi-commercial-invoice-delete-dialog',
    templateUrl: './commercial-invoice-delete-dialog.component.html'
})
export class CommercialInvoiceDeleteDialogComponent {
    commercialInvoice: ICommercialInvoice;

    constructor(
        protected commercialInvoiceService: CommercialInvoiceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialInvoiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialInvoiceListModification',
                content: 'Deleted an commercialInvoice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-invoice-delete-popup',
    template: ''
})
export class CommercialInvoiceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialInvoice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialInvoiceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialInvoice = commercialInvoice;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-invoice', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-invoice', { outlets: { popup: null } }]);
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
