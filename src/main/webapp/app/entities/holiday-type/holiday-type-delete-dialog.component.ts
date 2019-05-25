import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from './holiday-type.service';

@Component({
    selector: 'jhi-holiday-type-delete-dialog',
    templateUrl: './holiday-type-delete-dialog.component.html'
})
export class HolidayTypeDeleteDialogComponent {
    holidayType: IHolidayType;

    constructor(
        protected holidayTypeService: HolidayTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.holidayTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'holidayTypeListModification',
                content: 'Deleted an holidayType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-holiday-type-delete-popup',
    template: ''
})
export class HolidayTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holidayType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HolidayTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.holidayType = holidayType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/holiday-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/holiday-type', { outlets: { popup: null } }]);
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
