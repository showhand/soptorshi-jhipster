import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from './monthly-salary.service';

@Component({
    selector: 'jhi-monthly-salary-delete-dialog',
    templateUrl: './monthly-salary-delete-dialog.component.html'
})
export class MonthlySalaryDeleteDialogComponent {
    monthlySalary: IMonthlySalary;

    constructor(
        protected monthlySalaryService: MonthlySalaryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.monthlySalaryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'monthlySalaryListModification',
                content: 'Deleted an monthlySalary'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-monthly-salary-delete-popup',
    template: ''
})
export class MonthlySalaryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlySalary }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MonthlySalaryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.monthlySalary = monthlySalary;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/monthly-salary', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/monthly-salary', { outlets: { popup: null } }]);
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
