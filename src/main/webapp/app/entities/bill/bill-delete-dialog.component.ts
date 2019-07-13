import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBill } from 'app/shared/model/bill.model';
import { BillService } from './bill.service';

@Component({
    selector: 'jhi-bill-delete-dialog',
    templateUrl: './bill-delete-dialog.component.html'
})
export class BillDeleteDialogComponent {
    bill: IBill;

    constructor(protected billService: BillService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.billService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'billListModification',
                content: 'Deleted an bill'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bill-delete-popup',
    template: ''
})
export class BillDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bill }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BillDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bill = bill;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/bill', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/bill', { outlets: { popup: null } }]);
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
