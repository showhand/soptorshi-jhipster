import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';
import { CommercialProformaInvoiceService } from './commercial-proforma-invoice.service';

@Component({
    selector: 'jhi-commercial-proforma-invoice-delete-dialog',
    templateUrl: './commercial-proforma-invoice-delete-dialog.component.html'
})
export class CommercialProformaInvoiceDeleteDialogComponent {
    commercialProformaInvoice: ICommercialProformaInvoice;

    constructor(
        protected commercialProformaInvoiceService: CommercialProformaInvoiceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-commercial-proforma-invoice-delete-popup',
    template: ''
})
export class CommercialProformaInvoiceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialProformaInvoice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialProformaInvoiceDeleteDialogComponent as Component, {
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
