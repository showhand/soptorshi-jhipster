import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeExtendedService } from './holiday-type-extended.service';
import { HolidayTypeDeleteDialogComponent, HolidayTypeDeletePopupComponent } from 'app/entities/holiday-type';

@Component({
    selector: 'jhi-holiday-type-delete-dialog-extended',
    templateUrl: './holiday-type-delete-dialog-extended.component.html'
})
export class HolidayTypeDeleteDialogExtendedComponent extends HolidayTypeDeleteDialogComponent {
    holidayType: IHolidayType;

    constructor(
        protected holidayTypeService: HolidayTypeExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(holidayTypeService, activeModal, eventManager);
    }

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
    selector: 'jhi-holiday-type-delete-popup-extended',
    template: ''
})
export class HolidayTypeDeletePopupComponentExtended extends HolidayTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holidayType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HolidayTypeDeleteDialogExtendedComponent as Component, {
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
