import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';
import { AcademicInformationAttachmentService } from './academic-information-attachment.service';

@Component({
    selector: 'jhi-academic-information-attachment-delete-dialog',
    templateUrl: './academic-information-attachment-delete-dialog.component.html'
})
export class AcademicInformationAttachmentDeleteDialogComponent {
    academicInformationAttachment: IAcademicInformationAttachment;

    constructor(
        protected academicInformationAttachmentService: AcademicInformationAttachmentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.academicInformationAttachmentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'academicInformationAttachmentListModification',
                content: 'Deleted an academicInformationAttachment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-academic-information-attachment-delete-popup',
    template: ''
})
export class AcademicInformationAttachmentDeletePopupComponent implements OnInit, OnDestroy {
    @Input()
    academicInformationAttachment: IAcademicInformationAttachment;
    @Output()
    showAcademicInformationAttachment: EventEmitter<any> = new EventEmitter();
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        setTimeout(() => {
            this.ngbModalRef = this.modalService.open(AcademicInformationAttachmentDeleteDialogComponent as Component, {
                size: 'lg',
                backdrop: 'static'
            });
            this.ngbModalRef.componentInstance.academicInformationAttachment = this.academicInformationAttachment;
            this.ngbModalRef.result.then(
                result => {
                    this.showAcademicInformationAttachment.emit();
                    this.ngbModalRef = null;
                },
                reason => {
                    this.showAcademicInformationAttachment.emit();
                    this.ngbModalRef = null;
                }
            );
        }, 0);
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
