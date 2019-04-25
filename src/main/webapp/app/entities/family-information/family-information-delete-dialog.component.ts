import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamilyInformation } from 'app/shared/model/family-information.model';
import { FamilyInformationService } from './family-information.service';

@Component({
    selector: 'jhi-family-information-delete-dialog',
    templateUrl: './family-information-delete-dialog.component.html'
})
export class FamilyInformationDeleteDialogComponent {
    familyInformation: IFamilyInformation;

    constructor(
        protected familyInformationService: FamilyInformationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.familyInformationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'familyInformationListModification',
                content: 'Deleted an familyInformation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-family-information-delete-popup',
    template: ''
})
export class FamilyInformationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ familyInformation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FamilyInformationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.familyInformation = familyInformation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/family-information', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/family-information', { outlets: { popup: null } }]);
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
