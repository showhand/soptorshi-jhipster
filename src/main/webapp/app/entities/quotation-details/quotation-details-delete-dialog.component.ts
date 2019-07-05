import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuotationDetails } from 'app/shared/model/quotation-details.model';
import { QuotationDetailsService } from './quotation-details.service';

@Component({
    selector: 'jhi-quotation-details-delete-dialog',
    templateUrl: './quotation-details-delete-dialog.component.html'
})
export class QuotationDetailsDeleteDialogComponent {
    quotationDetails: IQuotationDetails;

    constructor(
        protected quotationDetailsService: QuotationDetailsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.quotationDetailsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'quotationDetailsListModification',
                content: 'Deleted an quotationDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-quotation-details-delete-popup',
    template: ''
})
export class QuotationDetailsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quotationDetails }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuotationDetailsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.quotationDetails = quotationDetails;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/quotation-details', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/quotation-details', { outlets: { popup: null } }]);
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
