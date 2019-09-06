import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMonthlyBalance } from 'app/shared/model/monthly-balance.model';
import { MonthlyBalanceService } from './monthly-balance.service';

@Component({
    selector: 'jhi-monthly-balance-delete-dialog',
    templateUrl: './monthly-balance-delete-dialog.component.html'
})
export class MonthlyBalanceDeleteDialogComponent {
    monthlyBalance: IMonthlyBalance;

    constructor(
        protected monthlyBalanceService: MonthlyBalanceService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.monthlyBalanceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'monthlyBalanceListModification',
                content: 'Deleted an monthlyBalance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-monthly-balance-delete-popup',
    template: ''
})
export class MonthlyBalanceDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlyBalance }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MonthlyBalanceDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.monthlyBalance = monthlyBalance;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/monthly-balance', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/monthly-balance', { outlets: { popup: null } }]);
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
