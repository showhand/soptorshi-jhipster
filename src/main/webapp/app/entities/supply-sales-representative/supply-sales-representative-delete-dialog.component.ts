import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';
import { SupplySalesRepresentativeService } from './supply-sales-representative.service';

@Component({
    selector: 'jhi-supply-sales-representative-delete-dialog',
    templateUrl: './supply-sales-representative-delete-dialog.component.html'
})
export class SupplySalesRepresentativeDeleteDialogComponent {
    supplySalesRepresentative: ISupplySalesRepresentative;

    constructor(
        protected supplySalesRepresentativeService: SupplySalesRepresentativeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.supplySalesRepresentativeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'supplySalesRepresentativeListModification',
                content: 'Deleted an supplySalesRepresentative'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-supply-sales-representative-delete-popup',
    template: ''
})
export class SupplySalesRepresentativeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplySalesRepresentative }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SupplySalesRepresentativeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.supplySalesRepresentative = supplySalesRepresentative;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/supply-sales-representative', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/supply-sales-representative', { outlets: { popup: null } }]);
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
