import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from './financial-account-year.service';

@Component({
    selector: 'jhi-financial-account-year-delete-dialog',
    templateUrl: './financial-account-year-delete-dialog.component.html'
})
export class FinancialAccountYearDeleteDialogComponent {
    financialAccountYear: IFinancialAccountYear;

    constructor(
        protected financialAccountYearService: FinancialAccountYearService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.financialAccountYearService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'financialAccountYearListModification',
                content: 'Deleted an financialAccountYear'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-financial-account-year-delete-popup',
    template: ''
})
export class FinancialAccountYearDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ financialAccountYear }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FinancialAccountYearDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.financialAccountYear = financialAccountYear;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/financial-account-year', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/financial-account-year', { outlets: { popup: null } }]);
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
