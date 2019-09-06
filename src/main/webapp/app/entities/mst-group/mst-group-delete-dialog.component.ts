import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupService } from './mst-group.service';

@Component({
    selector: 'jhi-mst-group-delete-dialog',
    templateUrl: './mst-group-delete-dialog.component.html'
})
export class MstGroupDeleteDialogComponent {
    mstGroup: IMstGroup;

    constructor(protected mstGroupService: MstGroupService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mstGroupService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mstGroupListModification',
                content: 'Deleted an mstGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mst-group-delete-popup',
    template: ''
})
export class MstGroupDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mstGroup }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MstGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.mstGroup = mstGroup;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/mst-group', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/mst-group', { outlets: { popup: null } }]);
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
