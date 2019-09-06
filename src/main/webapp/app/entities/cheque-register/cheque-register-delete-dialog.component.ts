import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChequeRegister } from 'app/shared/model/cheque-register.model';
import { ChequeRegisterService } from './cheque-register.service';

@Component({
    selector: 'jhi-cheque-register-delete-dialog',
    templateUrl: './cheque-register-delete-dialog.component.html'
})
export class ChequeRegisterDeleteDialogComponent {
    chequeRegister: IChequeRegister;

    constructor(
        protected chequeRegisterService: ChequeRegisterService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chequeRegisterService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'chequeRegisterListModification',
                content: 'Deleted an chequeRegister'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cheque-register-delete-popup',
    template: ''
})
export class ChequeRegisterDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chequeRegister }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ChequeRegisterDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.chequeRegister = chequeRegister;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/cheque-register', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/cheque-register', { outlets: { popup: null } }]);
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
