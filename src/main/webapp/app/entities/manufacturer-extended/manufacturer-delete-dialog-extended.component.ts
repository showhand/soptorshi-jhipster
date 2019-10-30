import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerExtendedService } from './manufacturer-extended.service';
import { ManufacturerDeleteDialogComponent, ManufacturerDeletePopupComponent } from 'app/entities/manufacturer';

@Component({
    selector: 'jhi-manufacturer-delete-dialog-extended',
    templateUrl: './manufacturer-delete-dialog-extended.component.html'
})
export class ManufacturerDeleteDialogExtendedComponent extends ManufacturerDeleteDialogComponent {
    manufacturer: IManufacturer;

    constructor(
        protected manufacturerService: ManufacturerExtendedService,
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
                this.ngbModalRef = this.modalService.open(ManufacturerDeleteDialogExtendedComponent as Component, {
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
