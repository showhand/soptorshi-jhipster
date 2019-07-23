import { VendorContactPersonExtendedUpdateComponent } from 'app/entities/vendor-contact-person-extended';
import { VendorService, VendorUpdateComponent } from 'app/entities/vendor';
import { JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VendorContactPersonDeleteDialogComponent } from 'app/entities/vendor-contact-person';
import { IVendor, Vendor } from 'app/shared/model/vendor.model';

@Component({
    selector: 'jhi-vendor-extended-update-dialog',
    templateUrl: './vendor-extended-update-dialog.component.html'
})
export class VendorExtendedUpdateDialogComponent implements OnInit {
    vendor: IVendor;
    constructor(
        protected dataUtils: JhiDataUtils,
        protected vendorService: VendorService,
        protected activatedRoute: ActivatedRoute,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    ngOnInit(): void {
        this.vendor = new Vendor();
    }

    confirmAdd() {
        this.vendorService.create(this.vendor).subscribe(response => {
            this.eventManager.broadcast({
                name: 'vendorListModification',
                content: 'Vendor Created'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vendor-extended-update-popup',
    template: ''
})
export class VendorExtendedUpdatedPopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        setTimeout(() => {
            this.ngbModalRef = this.modalService.open(VendorExtendedUpdateDialogComponent as Component, {
                size: 'lg',
                backdrop: 'static'
            });
            this.ngbModalRef.componentInstance.vendor = new Vendor();
            this.ngbModalRef.result.then(
                result => {
                    this.router.navigate(['/vendor', { outlets: { popup: null } }]);
                    this.ngbModalRef = null;
                },
                reason => {
                    this.router.navigate(['/vendor', { outlets: { popup: null } }]);
                    this.ngbModalRef = null;
                }
            );
        }, 0);
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
