import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerService } from './manufacturer.service';

@Component({
    selector: 'jhi-manufacturer-delete-dialog',
    templateUrl: './manufacturer-delete-dialog.component.html'
})
export class ManufacturerDeleteDialogComponent {
    manufacturer: IManufacturer;

    constructor(
        protected manufacturerService: ManufacturerService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-manufacturer-delete-popup',
    template: ''
})
export class ManufacturerDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ manufacturer }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ManufacturerDeleteDialogComponent as Component, {
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
