import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProvidentFund } from 'app/shared/model/provident-fund.model';
import { ProvidentFundService } from './provident-fund.service';

@Component({
    selector: 'jhi-provident-fund-delete-dialog',
    templateUrl: './provident-fund-delete-dialog.component.html'
})
export class ProvidentFundDeleteDialogComponent {
    providentFund: IProvidentFund;

    constructor(
        protected providentFundService: ProvidentFundService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.providentFundService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'providentFundListModification',
                content: 'Deleted an providentFund'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-provident-fund-delete-popup',
    template: ''
})
export class ProvidentFundDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ providentFund }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProvidentFundDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.providentFund = providentFund;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/provident-fund', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/provident-fund', { outlets: { popup: null } }]);
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
