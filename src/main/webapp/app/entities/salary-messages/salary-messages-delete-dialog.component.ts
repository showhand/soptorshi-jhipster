import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalaryMessages } from 'app/shared/model/salary-messages.model';
import { SalaryMessagesService } from './salary-messages.service';

@Component({
    selector: 'jhi-salary-messages-delete-dialog',
    templateUrl: './salary-messages-delete-dialog.component.html'
})
export class SalaryMessagesDeleteDialogComponent {
    salaryMessages: ISalaryMessages;

    constructor(
        protected salaryMessagesService: SalaryMessagesService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.salaryMessagesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'salaryMessagesListModification',
                content: 'Deleted an salaryMessages'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-salary-messages-delete-popup',
    template: ''
})
export class SalaryMessagesDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salaryMessages }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SalaryMessagesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.salaryMessages = salaryMessages;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/salary-messages', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/salary-messages', { outlets: { popup: null } }]);
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
