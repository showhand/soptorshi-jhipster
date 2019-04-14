import { Component, OnInit, OnDestroy, EventEmitter, Output, Input } from '@angular/core';
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
    @Input()
    experienceInformationAttachment: IExperienceInformationAttachment;
    @Output()
    showExperienceInformationSection: EventEmitter<any> = new EventEmitter();

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        setTimeout(() => {
            this.ngbModalRef = this.modalService.open(ExperienceInformationAttachmentDeleteDialogComponent as Component, {
                size: 'lg',
                backdrop: 'static'
            });
            this.ngbModalRef.componentInstance.experienceInformationAttachment = this.experienceInformationAttachment;
            this.ngbModalRef.result.then(
                result => {
                    this.showExperienceInformationSection.emit();
                    this.ngbModalRef = null;
                },
                reason => {
                    this.showExperienceInformationSection.emit();
                    this.ngbModalRef = null;
                }
            );
        }, 0);
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
