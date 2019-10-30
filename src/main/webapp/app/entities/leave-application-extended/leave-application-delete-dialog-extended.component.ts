import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationExtendedService } from './leave-application-extended.service';
import { LeaveApplicationDeleteDialogComponent, LeaveApplicationDeletePopupComponent } from 'app/entities/leave-application';

@Component({
    selector: 'jhi-leave-application-delete-dialog-extended',
    templateUrl: './leave-application-delete-dialog-extended.component.html'
})
export class LeaveApplicationDeleteDialogExtendedComponent extends LeaveApplicationDeleteDialogComponent {
    leaveApplication: ILeaveApplication;

    constructor(
        protected leaveApplicationService: LeaveApplicationExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(leaveApplicationService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.leaveApplicationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'leaveApplicationListModification',
                content: 'Deleted an leaveApplication'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-leave-application-delete-popup-extended',
    template: ''
})
export class LeaveApplicationDeletePopupComponentExtended extends LeaveApplicationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveApplication }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LeaveApplicationDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.leaveApplication = leaveApplication;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/leave-application', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/leave-application', { outlets: { popup: null } }]);
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
