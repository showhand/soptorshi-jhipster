import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryExtendedService } from './item-category-extended.service';
import { ItemCategoryDeleteDialogComponent, ItemCategoryDeletePopupComponent } from 'app/entities/item-category';

@Component({
    selector: 'jhi-item-category-delete-dialog-extended',
    templateUrl: './item-category-delete-dialog-extended.component.html'
})
export class ItemCategoryDeleteDialogExtendedComponent extends ItemCategoryDeleteDialogComponent {
    itemCategory: IItemCategory;

    constructor(
        protected itemCategoryService: ItemCategoryExtendedService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(itemCategoryService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'itemCategoryListModification',
                content: 'Deleted an itemCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-category-delete-popup-extended',
    template: ''
})
export class ItemCategoryDeletePopupComponentExtended extends ItemCategoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemCategoryDeleteDialogExtendedComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.itemCategory = itemCategory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/item-category', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/item-category', { outlets: { popup: null } }]);
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
