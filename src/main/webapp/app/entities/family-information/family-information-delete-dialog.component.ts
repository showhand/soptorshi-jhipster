import { Component, OnInit, OnDestroy, EventEmitter, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamilyInformation } from 'app/shared/model/family-information.model';
import { FamilyInformationService } from './family-information.service';

@Component({
    selector: 'jhi-family-information-delete-dialog',
    templateUrl: './family-information-delete-dialog.component.html'
})
export class FamilyInformationDeleteDialogComponent {
    familyInformation: IFamilyInformation;

    constructor(
        protected familyInformationService: FamilyInformationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.familyInformationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'familyInformationListModification',
                content: 'Deleted an familyInformation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-family-information-delete-popup',
    template: ''
})
export class FamilyInformationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;
    @Input()
    familyInformation: IFamilyInformation;
    @Output()
    showFamilyInformationSection: EventEmitter<any> = new EventEmitter();
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        setTimeout(() => {
            this.ngbModalRef = this.modalService.open(FamilyInformationDeleteDialogComponent as Component, {
                size: 'lg',
                backdrop: 'static'
            });
            this.ngbModalRef.componentInstance.familyInformation = this.familyInformation;
            this.ngbModalRef.result.then(
                result => {
                    this.showFamilyInformationSection.emit();
                    this.ngbModalRef = null;
                },
                reason => {
                    this.showFamilyInformationSection.emit();
                    this.ngbModalRef = null;
                }
            );
        }, 0);
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
