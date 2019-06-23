import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';
import { SpecialAllowanceTimeLineService } from './special-allowance-time-line.service';

@Component({
    selector: 'jhi-special-allowance-time-line-delete-dialog',
    templateUrl: './special-allowance-time-line-delete-dialog.component.html'
})
export class SpecialAllowanceTimeLineDeleteDialogComponent {
    specialAllowanceTimeLine: ISpecialAllowanceTimeLine;

    constructor(
        protected specialAllowanceTimeLineService: SpecialAllowanceTimeLineService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.specialAllowanceTimeLineService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'specialAllowanceTimeLineListModification',
                content: 'Deleted an specialAllowanceTimeLine'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-special-allowance-time-line-delete-popup',
    template: ''
})
export class SpecialAllowanceTimeLineDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ specialAllowanceTimeLine }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SpecialAllowanceTimeLineDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.specialAllowanceTimeLine = specialAllowanceTimeLine;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/special-allowance-time-line', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/special-allowance-time-line', { outlets: { popup: null } }]);
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
