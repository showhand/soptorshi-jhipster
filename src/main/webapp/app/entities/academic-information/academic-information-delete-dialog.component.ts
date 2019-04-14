import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
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
    @Input()
    academicInformation: IAcademicInformation;
    @Output()
    showAcademicInformationSection: EventEmitter<any> = new EventEmitter();

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        setTimeout(() => {
            this.ngbModalRef = this.modalService.open(AcademicInformationDeleteDialogComponent as Component, {
                size: 'lg',
                backdrop: 'static'
            });
            this.ngbModalRef.componentInstance.academicInformation = this.academicInformation;
            this.ngbModalRef.result.then(
                result => {
                    this.showAcademicInformationSection.emit();
                    this.ngbModalRef = null;
                },
                reason => {
                    this.showAcademicInformationSection.emit();
                    this.ngbModalRef = null;
                }
            );
        }, 0);
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
