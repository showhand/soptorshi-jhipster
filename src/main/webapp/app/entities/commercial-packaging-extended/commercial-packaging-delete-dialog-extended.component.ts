import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';
import { CommercialPackagingExtendedService } from './commercial-packaging-extended.service';
import { CommercialPackagingDeleteDialogComponent, CommercialPackagingDeletePopupComponent } from 'app/entities/commercial-packaging';

@Component({
    selector: 'jhi-commercial-packaging-delete-dialog-extended',
    templateUrl: './commercial-packaging-delete-dialog-extended.component.html'
})
export class CommercialPackagingDeleteDialogExtendedComponent extends CommercialPackagingDeleteDialogComponent {
    commercialPackaging: ICommercialPackaging;

    constructor(
        protected commercialPackagingService: CommercialPackagingExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(commercialPackagingService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commercialPackagingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commercialPackagingListModification',
                content: 'Deleted an commercialPackaging'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commercial-packaging-delete-popup-extended',
    template: ''
})
export class CommercialPackagingDeletePopupExtendedComponent extends CommercialPackagingDeletePopupComponent {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPackaging }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPackagingDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.commercialPackaging = commercialPackaging;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commercial-packaging', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commercial-packaging', { outlets: { popup: null } }]);
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
