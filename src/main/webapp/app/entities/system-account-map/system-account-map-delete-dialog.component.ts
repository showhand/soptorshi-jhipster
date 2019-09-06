import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';
import { SystemAccountMapService } from './system-account-map.service';

@Component({
    selector: 'jhi-system-account-map-delete-dialog',
    templateUrl: './system-account-map-delete-dialog.component.html'
})
export class SystemAccountMapDeleteDialogComponent {
    systemAccountMap: ISystemAccountMap;

    constructor(
        protected systemAccountMapService: SystemAccountMapService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.systemAccountMapService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'systemAccountMapListModification',
                content: 'Deleted an systemAccountMap'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-system-account-map-delete-popup',
    template: ''
})
export class SystemAccountMapDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ systemAccountMap }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SystemAccountMapDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.systemAccountMap = systemAccountMap;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/system-account-map', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/system-account-map', { outlets: { popup: null } }]);
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
