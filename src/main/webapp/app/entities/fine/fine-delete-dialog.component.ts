import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFine } from 'app/shared/model/fine.model';
import { FineService } from './fine.service';

@Component({
    selector: 'jhi-fine-delete-dialog',
    templateUrl: './fine-delete-dialog.component.html'
})
export class FineDeleteDialogComponent {
    fine: IFine;

    constructor(protected fineService: FineService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fineListModification',
                content: 'Deleted an fine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fine-delete-popup',
    template: ''
})
export class FineDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fine }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FineDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.fine = fine;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/fine', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/fine', { outlets: { popup: null } }]);
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
