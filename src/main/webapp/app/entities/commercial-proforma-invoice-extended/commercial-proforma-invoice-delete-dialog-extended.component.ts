import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';
import { CommercialProformaInvoiceExtendedService } from './commercial-proforma-invoice-extended.service';
import {
    CommercialProformaInvoiceDeleteDialogComponent,
    CommercialProformaInvoiceDeletePopupComponent
} from 'app/entities/commercial-proforma-invoice';

@Component({
    selector: 'jhi-commercial-proforma-invoice-delete-dialog-extended',
    templateUrl: './commercial-proforma-invoice-delete-dialog-extended.component.html'
})
export class CommercialProformaInvoiceDeleteDialogExtendedComponent extends CommercialProformaInvoiceDeleteDialogComponent {
    commercialProformaInvoice: ICommercialProformaInvoice;

    constructor(
        protected commercialProformaInvoiceService: CommercialProformaInvoiceExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialProformaInvoiceService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialProformaInvoiceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialProformaInvoiceListModification',
                content: 'Deleted an commercialProformaInvoice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-proforma-invoice-delete-popup-extended',
    template: ''
})
export class CommercialProformaInvoiceDeletePopupExtendedComponent extends CommercialProformaInvoiceDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialProformaInvoice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialProformaInvoiceDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialProformaInvoice = commercialProformaInvoice;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-proforma-invoice', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-proforma-invoice', { outlets: { popup: null } }]);
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
