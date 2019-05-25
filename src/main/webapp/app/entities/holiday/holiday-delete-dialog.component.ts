import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';

@Component({
    selector: 'jhi-holiday-delete-dialog',
    templateUrl: './holiday-delete-dialog.component.html'
})
export class HolidayDeleteDialogComponent {
    holiday: IHoliday;

    constructor(protected holidayService: HolidayService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

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
    selector: 'jhi-holiday-delete-popup',
    template: ''
})
export class HolidayDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holiday }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HolidayDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
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
