import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProvidentManagement } from 'app/shared/model/provident-management.model';
import { ProvidentManagementService } from './provident-management.service';

@Component({
    selector: 'jhi-provident-management-delete-dialog',
    templateUrl: './provident-management-delete-dialog.component.html'
})
export class ProvidentManagementDeleteDialogComponent {
    providentManagement: IProvidentManagement;

    constructor(
        protected providentManagementService: ProvidentManagementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.providentManagementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'providentManagementListModification',
                content: 'Deleted an providentManagement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-provident-management-delete-popup',
    template: ''
})
export class ProvidentManagementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ providentManagement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProvidentManagementDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.providentManagement = providentManagement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/provident-management', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/provident-management', { outlets: { popup: null } }]);
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
