import {
    DepartmentHeadDeleteDialogComponent,
    DepartmentHeadDeletePopupComponent,
    DepartmentHeadService
} from 'app/entities/department-head';
import { Component } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'jhi-department-head-extended-delete-dialog',
    templateUrl: './department-head-extended-delete-dialog.component.html'
})
export class DepartmentHeadExtendedDeleteDialogComponent extends DepartmentHeadDeleteDialogComponent {
    constructor(
        protected departmentHeadService: DepartmentHeadService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(departmentHeadService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-department-head-extended-delete-popup',
    template: ''
})
export class DepartmentHeadExtendedDeletePopupComponent extends DepartmentHeadDeletePopupComponent {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }
}
