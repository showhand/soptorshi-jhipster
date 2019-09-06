import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { ConversionFactorService } from './conversion-factor.service';

@Component({
    selector: 'jhi-conversion-factor-delete-dialog',
    templateUrl: './conversion-factor-delete-dialog.component.html'
})
export class ConversionFactorDeleteDialogComponent {
    conversionFactor: IConversionFactor;

    constructor(
        protected conversionFactorService: ConversionFactorService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.conversionFactorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'conversionFactorListModification',
                content: 'Deleted an conversionFactor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-conversion-factor-delete-popup',
    template: ''
})
export class ConversionFactorDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ conversionFactor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConversionFactorDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.conversionFactor = conversionFactor;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/conversion-factor', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/conversion-factor', { outlets: { popup: null } }]);
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
