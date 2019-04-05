import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAcademicInformation } from 'app/shared/model/academic-information.model';
import { AcademicInformationService } from './academic-information.service';

@Component({
    selector: 'jhi-academic-information-delete-dialog',
    templateUrl: './academic-information-delete-dialog.component.html'
})
export class AcademicInformationDeleteDialogComponent {
    academicInformation: IAcademicInformation;

    constructor(
        protected academicInformationService: AcademicInformationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.academicInformationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'academicInformationListModification',
                content: 'Deleted an academicInformation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-academic-information-delete-popup',
    template: ''
})
export class AcademicInformationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ academicInformation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AcademicInformationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.academicInformation = academicInformation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/academic-information', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/academic-information', { outlets: { popup: null } }]);
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
