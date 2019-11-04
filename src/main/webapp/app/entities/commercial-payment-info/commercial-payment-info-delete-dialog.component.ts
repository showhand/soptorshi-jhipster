import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';
import { CommercialPaymentInfoService } from './commercial-payment-info.service';

@Component({
    selector: 'jhi-commercial-payment-info-delete-dialog',
    templateUrl: './commercial-payment-info-delete-dialog.component.html'
})
export class CommercialPaymentInfoDeleteDialogComponent {
    commercialPaymentInfo: ICommercialPaymentInfo;

    constructor(
        protected commercialPaymentInfoService: CommercialPaymentInfoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialPaymentInfoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialPaymentInfoListModification',
                content: 'Deleted an commercialPaymentInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-payment-info-delete-popup',
    template: ''
})
export class CommercialPaymentInfoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPaymentInfo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPaymentInfoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialPaymentInfo = commercialPaymentInfo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-payment-info', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-payment-info', { outlets: { popup: null } }]);
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
