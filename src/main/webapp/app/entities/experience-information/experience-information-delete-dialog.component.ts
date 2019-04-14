import { Component, OnInit, OnDestroy, EventEmitter, Input, Output } from '@angular/core';
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
    @Input()
    experienceInformation: IExperienceInformation;
    @Output()
    showExperienceInformationSection: EventEmitter<any> = new EventEmitter();

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        setTimeout(() => {
            this.ngbModalRef = this.modalService.open(ExperienceInformationDeleteDialogComponent as Component, {
                size: 'lg',
                backdrop: 'static'
            });
            this.ngbModalRef.componentInstance.experienceInformation = this.experienceInformation;
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
