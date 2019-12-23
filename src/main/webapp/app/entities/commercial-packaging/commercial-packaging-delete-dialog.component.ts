import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';
import { CommercialPackagingService } from './commercial-packaging.service';

@Component({
    selector: 'jhi-commercial-packaging-delete-dialog',
    templateUrl: './commercial-packaging-delete-dialog.component.html'
})
export class CommercialPackagingDeleteDialogComponent {
    commercialPackaging: ICommercialPackaging;

    constructor(
        protected commercialPackagingService: CommercialPackagingService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-commercial-packaging-delete-popup',
    template: ''
})
export class CommercialPackagingDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPackaging }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommercialPackagingDeleteDialogComponent as Component, {
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
