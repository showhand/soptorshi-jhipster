import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAllowanceManagement } from 'app/shared/model/allowance-management.model';
import { AllowanceManagementService } from './allowance-management.service';

@Component({
    selector: 'jhi-allowance-management-delete-dialog',
    templateUrl: './allowance-management-delete-dialog.component.html'
})
export class AllowanceManagementDeleteDialogComponent {
    allowanceManagement: IAllowanceManagement;

    constructor(
        protected allowanceManagementService: AllowanceManagementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {}
}

@Component({
    selector: 'jhi-allowance-management-delete-popup',
    template: ''
})
export class AllowanceManagementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {}

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
