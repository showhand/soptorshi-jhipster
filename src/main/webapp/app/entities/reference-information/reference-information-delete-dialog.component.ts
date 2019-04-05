import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReferenceInformation } from 'app/shared/model/reference-information.model';
import { ReferenceInformationService } from './reference-information.service';

@Component({
    selector: 'jhi-reference-information-delete-dialog',
    templateUrl: './reference-information-delete-dialog.component.html'
})
export class ReferenceInformationDeleteDialogComponent {
    referenceInformation: IReferenceInformation;

    constructor(
        protected referenceInformationService: ReferenceInformationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.referenceInformationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'referenceInformationListModification',
                content: 'Deleted an referenceInformation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reference-information-delete-popup',
    template: ''
})
export class ReferenceInformationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ referenceInformation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReferenceInformationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.referenceInformation = referenceInformation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/reference-information', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/reference-information', { outlets: { popup: null } }]);
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
