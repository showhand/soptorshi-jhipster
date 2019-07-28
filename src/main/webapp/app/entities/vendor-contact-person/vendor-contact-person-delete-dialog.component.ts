import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVendorContactPerson } from 'app/shared/model/vendor-contact-person.model';
import { VendorContactPersonService } from './vendor-contact-person.service';
import { StateStorageService } from 'app/core';

@Component({
    selector: 'jhi-vendor-contact-person-delete-dialog',
    templateUrl: './vendor-contact-person-delete-dialog.component.html'
})
export class VendorContactPersonDeleteDialogComponent {
    vendorContactPerson: IVendorContactPerson;

    constructor(
        protected vendorContactPersonService: VendorContactPersonService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vendorContactPersonService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vendorContactPersonListModification',
                content: 'Deleted an vendorContactPerson'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vendor-contact-person-delete-popup',
    template: ''
})
export class VendorContactPersonDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected modalService: NgbModal,
        protected stateStorageService: StateStorageService
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ vendorContactPerson }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VendorContactPersonDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.vendorContactPerson = vendorContactPerson;
                const redirect = this.stateStorageService.getUrl();
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/vendor-contact-person', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/vendor-contact-person', { outlets: { popup: null } }]);
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
