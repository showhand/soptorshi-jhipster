import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMontlySalaryVouchers } from 'app/shared/model/montly-salary-vouchers.model';
import { MontlySalaryVouchersService } from './montly-salary-vouchers.service';

@Component({
    selector: 'jhi-montly-salary-vouchers-delete-dialog',
    templateUrl: './montly-salary-vouchers-delete-dialog.component.html'
})
export class MontlySalaryVouchersDeleteDialogComponent {
    montlySalaryVouchers: IMontlySalaryVouchers;

    constructor(
        protected montlySalaryVouchersService: MontlySalaryVouchersService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.montlySalaryVouchersService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'montlySalaryVouchersListModification',
                content: 'Deleted an montlySalaryVouchers'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-montly-salary-vouchers-delete-popup',
    template: ''
})
export class MontlySalaryVouchersDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ montlySalaryVouchers }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MontlySalaryVouchersDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.montlySalaryVouchers = montlySalaryVouchers;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/montly-salary-vouchers', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/montly-salary-vouchers', { outlets: { popup: null } }]);
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
