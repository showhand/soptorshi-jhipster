import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepartmentHead } from 'app/shared/model/department-head.model';
import { DepartmentHeadService } from './department-head.service';

@Component({
    selector: 'jhi-department-head-delete-dialog',
    templateUrl: './department-head-delete-dialog.component.html'
})
export class DepartmentHeadDeleteDialogComponent {
    departmentHead: IDepartmentHead;

    constructor(
        protected departmentHeadService: DepartmentHeadService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.departmentHeadService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'departmentHeadListModification',
                content: 'Deleted an departmentHead'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-department-head-delete-popup',
    template: ''
})
export class DepartmentHeadDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ departmentHead }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DepartmentHeadDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.departmentHead = departmentHead;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/department-head', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/department-head', { outlets: { popup: null } }]);
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
