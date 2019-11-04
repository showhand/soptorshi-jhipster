import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';
import { CommercialPaymentInfoExtendedService } from './commercial-payment-info-extended.service';
import {
    CommercialPaymentInfoDeleteDialogComponent,
    CommercialPaymentInfoDeletePopupComponent
} from 'app/entities/commercial-payment-info';

@Component({
    selector: 'jhi-commercial-payment-info-delete-dialog-extended',
    templateUrl: './commercial-payment-info-delete-dialog-extended.component.html'
})
export class CommercialPaymentInfoDeleteDialogExtendedComponent extends CommercialPaymentInfoDeleteDialogComponent {
    commercialPaymentInfo: ICommercialPaymentInfo;

    constructor(
        protected commercialPaymentInfoService: CommercialPaymentInfoExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialPaymentInfoService, activeModal, eventManager);
    }

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
    selector: 'jhi-commercial-payment-info-delete-popup-extended',
    template: ''
})
export class CommercialPaymentInfoDeletePopupExtendedComponent extends CommercialPaymentInfoDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPaymentInfo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPaymentInfoDeleteDialogExtendedComponent as Component, {
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
