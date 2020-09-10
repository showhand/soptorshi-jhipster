import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { LeaveTypeExtendedService } from './leave-type-extended.service';
import { LeaveTypeDeleteDialogComponent, LeaveTypeDeletePopupComponent } from 'app/entities/leave-type';

@Component({
    selector: 'jhi-leave-type-delete-dialog-extended',
    templateUrl: './leave-type-delete-dialog-extended.component.html'
})
export class LeaveTypeDeleteDialogExtendedComponent extends LeaveTypeDeleteDialogComponent {
    constructor(
        protected leaveTypeService: LeaveTypeExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(leaveTypeService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-leave-type-delete-popup-extended',
    template: ''
})
export class LeaveTypeDeletePopupExtendedComponent extends LeaveTypeDeletePopupComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LeaveTypeDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
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
}
