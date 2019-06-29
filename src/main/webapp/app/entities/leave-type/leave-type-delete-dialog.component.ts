import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from './leave-type.service';

@Component({
    selector: 'jhi-leave-type-delete-dialog',
    templateUrl: './leave-type-delete-dialog.component.html'
})
export class LeaveTypeDeleteDialogComponent {
    leaveType: ILeaveType;

    constructor(
        protected leaveTypeService: LeaveTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.leaveTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'leaveTypeListModification',
                content: 'Deleted an leaveType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-leave-type-delete-popup',
    template: ''
})
export class LeaveTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LeaveTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.leaveType = leaveType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/leave-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/leave-type', { outlets: { popup: null } }]);
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
