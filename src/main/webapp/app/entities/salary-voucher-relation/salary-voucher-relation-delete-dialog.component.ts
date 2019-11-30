import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';
import { SalaryVoucherRelationService } from './salary-voucher-relation.service';

@Component({
    selector: 'jhi-salary-voucher-relation-delete-dialog',
    templateUrl: './salary-voucher-relation-delete-dialog.component.html'
})
export class SalaryVoucherRelationDeleteDialogComponent {
    salaryVoucherRelation: ISalaryVoucherRelation;

    constructor(
        protected salaryVoucherRelationService: SalaryVoucherRelationService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.salaryVoucherRelationService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'salaryVoucherRelationListModification',
                content: 'Deleted an salaryVoucherRelation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-salary-voucher-relation-delete-popup',
    template: ''
})
export class SalaryVoucherRelationDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salaryVoucherRelation }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SalaryVoucherRelationDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.salaryVoucherRelation = salaryVoucherRelation;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/salary-voucher-relation', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/salary-voucher-relation', { outlets: { popup: null } }]);
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
