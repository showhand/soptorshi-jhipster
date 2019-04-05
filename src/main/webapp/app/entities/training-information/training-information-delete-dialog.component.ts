import { Component, OnInit, OnDestroy } from '@angular/core';
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

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ trainingInformation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TrainingInformationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.trainingInformation = trainingInformation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/training-information', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/training-information', { outlets: { popup: null } }]);
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
