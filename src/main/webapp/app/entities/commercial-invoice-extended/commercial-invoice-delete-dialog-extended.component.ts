import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialInvoice } from 'app/shared/model/commercial-invoice.model';
import { CommercialInvoiceExtendedService } from './commercial-invoice-extended.service';
import { CommercialInvoiceDeleteDialogComponent, CommercialInvoiceDeletePopupComponent } from 'app/entities/commercial-invoice';

@Component({
    selector: 'jhi-commercial-invoice-delete-dialog-extended',
    templateUrl: './commercial-invoice-delete-dialog-extended.component.html'
})
export class CommercialInvoiceDeleteDialogExtendedComponent extends CommercialInvoiceDeleteDialogComponent {
    commercialInvoice: ICommercialInvoice;

    constructor(
        protected commercialInvoiceService: CommercialInvoiceExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialInvoiceService, activeModal, eventManager);
    }

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
    selector: 'jhi-commercial-invoice-delete-popup-extended',
    template: ''
})
export class CommercialInvoiceDeletePopupExtendedComponent extends CommercialInvoiceDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialInvoice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialInvoiceDeleteDialogExtendedComponent as Component, {
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
