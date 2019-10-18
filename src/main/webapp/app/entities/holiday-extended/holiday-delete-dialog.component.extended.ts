import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayServiceExtended } from './holiday.service.extended';
import { HolidayDeleteDialogComponent, HolidayDeletePopupComponent } from 'app/entities/holiday';

@Component({
    selector: 'jhi-holiday-delete-dialog-extended',
    templateUrl: './holiday-delete-dialog.component.extended.html'
})
export class HolidayDeleteDialogComponentExtended extends HolidayDeleteDialogComponent {
    holiday: IHoliday;

    constructor(
        protected holidayService: HolidayServiceExtended,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(holidayService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.holidayService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'holidayListModification',
                content: 'Deleted an holiday'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-holiday-delete-popup-extended',
    template: ''
})
export class HolidayDeletePopupComponentExtended extends HolidayDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holiday }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HolidayDeleteDialogComponentExtended as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.holiday = holiday;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/holiday', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/holiday', { outlets: { popup: null } }]);
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
