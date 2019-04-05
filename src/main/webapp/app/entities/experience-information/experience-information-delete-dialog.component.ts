import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExperienceInformation } from 'app/shared/model/experience-information.model';
import { ExperienceInformationService } from './experience-information.service';

@Component({
    selector: 'jhi-experience-information-delete-dialog',
    templateUrl: './experience-information-delete-dialog.component.html'
})
export class ExperienceInformationDeleteDialogComponent {
    experienceInformation: IExperienceInformation;

    constructor(
        protected experienceInformationService: ExperienceInformationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.experienceInformationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'experienceInformationListModification',
                content: 'Deleted an experienceInformation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-experience-information-delete-popup',
    template: ''
})
export class ExperienceInformationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experienceInformation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExperienceInformationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.experienceInformation = experienceInformation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/experience-information', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/experience-information', { outlets: { popup: null } }]);
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
