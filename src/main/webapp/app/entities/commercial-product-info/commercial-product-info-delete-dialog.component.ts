import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';
import { CommercialProductInfoService } from './commercial-product-info.service';

@Component({
    selector: 'jhi-commercial-product-info-delete-dialog',
    templateUrl: './commercial-product-info-delete-dialog.component.html'
})
export class CommercialProductInfoDeleteDialogComponent {
    commercialProductInfo: ICommercialProductInfo;

    constructor(
        protected commercialProductInfoService: CommercialProductInfoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialProductInfoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialProductInfoListModification',
                content: 'Deleted an commercialProductInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-product-info-delete-popup',
    template: ''
})
export class CommercialProductInfoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialProductInfo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialProductInfoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialProductInfo = commercialProductInfo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-product-info', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-product-info', { outlets: { popup: null } }]);
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
