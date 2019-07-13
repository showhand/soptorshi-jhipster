import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITermsAndConditions } from 'app/shared/model/terms-and-conditions.model';
import { TermsAndConditionsService } from './terms-and-conditions.service';

@Component({
    selector: 'jhi-terms-and-conditions-delete-dialog',
    templateUrl: './terms-and-conditions-delete-dialog.component.html'
})
export class TermsAndConditionsDeleteDialogComponent {
    termsAndConditions: ITermsAndConditions;

    constructor(
        protected termsAndConditionsService: TermsAndConditionsService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.termsAndConditionsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'termsAndConditionsListModification',
                content: 'Deleted an termsAndConditions'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-terms-and-conditions-delete-popup',
    template: ''
})
export class TermsAndConditionsDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ termsAndConditions }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TermsAndConditionsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.termsAndConditions = termsAndConditions;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/terms-and-conditions', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/terms-and-conditions', { outlets: { popup: null } }]);
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
