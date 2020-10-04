import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { ProductionExtendedService } from './production-extended.service';
import { ProductionDeleteDialogComponent, ProductionDeletePopupComponent } from 'app/entities/production';

@Component({
    selector: 'jhi-production-delete-dialog-extended',
    templateUrl: './production-delete-dialog-extended.component.html'
})
export class ProductionDeleteDialogExtendedComponent extends ProductionDeleteDialogComponent {
    constructor(
        protected productionService: ProductionExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(productionService, activeModal, eventManager);
    }
}

@Component({
    selector: 'jhi-production-delete-popup-extended',
    template: ''
})
export class ProductionDeletePopupExtendedComponent extends ProductionDeletePopupComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ production }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductionDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.production = production;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/production', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/production', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }
}
