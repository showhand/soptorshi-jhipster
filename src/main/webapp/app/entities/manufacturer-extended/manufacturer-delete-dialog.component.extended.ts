import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerServiceExtended } from './manufacturer.service.extended';
import { ManufacturerDeleteDialogComponent, ManufacturerDeletePopupComponent } from 'app/entities/manufacturer';

@Component({
    selector: 'jhi-manufacturer-delete-dialog-extended',
    templateUrl: './manufacturer-delete-dialog.component.extended.html'
})
export class ManufacturerDeleteDialogComponentExtended extends ManufacturerDeleteDialogComponent {
    manufacturer: IManufacturer;

    constructor(
        protected manufacturerService: ManufacturerServiceExtended,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(manufacturerService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.manufacturerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'manufacturerListModification',
                content: 'Deleted an manufacturer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-manufacturer-delete-popup-extended',
    template: ''
})
export class ManufacturerDeletePopupComponentExtended extends ManufacturerDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ manufacturer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ManufacturerDeleteDialogComponentExtended as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.manufacturer = manufacturer;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/manufacturer', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/manufacturer', { outlets: { popup: null } }]);
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
