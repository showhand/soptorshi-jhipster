import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { SystemGroupMapService } from './system-group-map.service';

@Component({
    selector: 'jhi-system-group-map-delete-dialog',
    templateUrl: './system-group-map-delete-dialog.component.html'
})
export class SystemGroupMapDeleteDialogComponent {
    systemGroupMap: ISystemGroupMap;

    constructor(
        protected systemGroupMapService: SystemGroupMapService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.systemGroupMapService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'systemGroupMapListModification',
                content: 'Deleted an systemGroupMap'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-system-group-map-delete-popup',
    template: ''
})
export class SystemGroupMapDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ systemGroupMap }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SystemGroupMapDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.systemGroupMap = systemGroupMap;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/system-group-map', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/system-group-map', { outlets: { popup: null } }]);
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
