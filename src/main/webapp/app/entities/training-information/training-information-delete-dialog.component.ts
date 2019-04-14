import { Component, OnInit, OnDestroy, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrainingInformation } from 'app/shared/model/training-information.model';
import { TrainingInformationService } from './training-information.service';

@Component({
    selector: 'jhi-training-information-delete-dialog',
    templateUrl: './training-information-delete-dialog.component.html'
})
export class TrainingInformationDeleteDialogComponent {
    trainingInformation: ITrainingInformation;

    constructor(
        protected trainingInformationService: TrainingInformationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.trainingInformationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'trainingInformationListModification',
                content: 'Deleted an trainingInformation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-training-information-delete-popup',
    template: ''
})
export class TrainingInformationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;
    @Input()
    trainingInformation: ITrainingInformation;
    @Output()
    showTrainingInformationSection: EventEmitter<any> = new EventEmitter();

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        setTimeout(() => {
            this.ngbModalRef = this.modalService.open(TrainingInformationDeleteDialogComponent as Component, {
                size: 'lg',
                backdrop: 'static'
            });
            this.ngbModalRef.componentInstance.trainingInformation = this.trainingInformation;
            this.ngbModalRef.result.then(
                result => {
                    this.showTrainingInformationSection.emit();
                    this.ngbModalRef = null;
                },
                reason => {
                    this.showTrainingInformationSection.emit();
                    this.ngbModalRef = null;
                }
            );
        }, 0);
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
