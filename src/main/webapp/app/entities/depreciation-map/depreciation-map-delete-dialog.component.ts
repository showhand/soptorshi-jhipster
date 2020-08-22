import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepreciationMap } from 'app/shared/model/depreciation-map.model';
import { DepreciationMapService } from './depreciation-map.service';

@Component({
    selector: 'jhi-depreciation-map-delete-dialog',
    templateUrl: './depreciation-map-delete-dialog.component.html'
})
export class DepreciationMapDeleteDialogComponent {
    depreciationMap: IDepreciationMap;

    constructor(
        protected depreciationMapService: DepreciationMapService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.depreciationMapService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'depreciationMapListModification',
                content: 'Deleted an depreciationMap'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-depreciation-map-delete-popup',
    template: ''
})
export class DepreciationMapDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depreciationMap }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DepreciationMapDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.depreciationMap = depreciationMap;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/depreciation-map', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/depreciation-map', { outlets: { popup: null } }]);
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
