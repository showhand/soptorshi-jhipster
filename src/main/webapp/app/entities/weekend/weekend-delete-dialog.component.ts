import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWeekend } from 'app/shared/model/weekend.model';
import { WeekendService } from './weekend.service';

@Component({
    selector: 'jhi-weekend-delete-dialog',
    templateUrl: './weekend-delete-dialog.component.html'
})
export class WeekendDeleteDialogComponent {
    weekend: IWeekend;

    constructor(protected weekendService: WeekendService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.weekendService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'weekendListModification',
                content: 'Deleted an weekend'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-weekend-delete-popup',
    template: ''
})
export class WeekendDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ weekend }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WeekendDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.weekend = weekend;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/weekend', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/weekend', { outlets: { popup: null } }]);
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
