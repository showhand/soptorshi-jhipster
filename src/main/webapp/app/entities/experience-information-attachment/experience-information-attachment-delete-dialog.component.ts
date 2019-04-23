import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';
import { ExperienceInformationAttachmentService } from './experience-information-attachment.service';

@Component({
    selector: 'jhi-experience-information-attachment-delete-dialog',
    templateUrl: './experience-information-attachment-delete-dialog.component.html'
})
export class ExperienceInformationAttachmentDeleteDialogComponent {
    experienceInformationAttachment: IExperienceInformationAttachment;

    constructor(
        protected experienceInformationAttachmentService: ExperienceInformationAttachmentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.experienceInformationAttachmentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'experienceInformationAttachmentListModification',
                content: 'Deleted an experienceInformationAttachment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-experience-information-attachment-delete-popup',
    template: ''
})
export class ExperienceInformationAttachmentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experienceInformationAttachment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExperienceInformationAttachmentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.experienceInformationAttachment = experienceInformationAttachment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/experience-information-attachment', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/experience-information-attachment', { outlets: { popup: null } }]);
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
