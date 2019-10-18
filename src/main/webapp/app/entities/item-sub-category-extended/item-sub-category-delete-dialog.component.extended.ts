import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryServiceExtended } from './item-sub-category.service.extended';
import { ItemSubCategoryDeleteDialogComponent, ItemSubCategoryDeletePopupComponent } from 'app/entities/item-sub-category';

@Component({
    selector: 'jhi-item-sub-category-delete-dialog-extended',
    templateUrl: './item-sub-category-delete-dialog.component.extended.html'
})
export class ItemSubCategoryDeleteDialogComponentExtended extends ItemSubCategoryDeleteDialogComponent {
    itemSubCategory: IItemSubCategory;

    constructor(
        protected itemSubCategoryService: ItemSubCategoryServiceExtended,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {
        super(itemSubCategoryService, activeModal, eventManager);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemSubCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'itemSubCategoryListModification',
                content: 'Deleted an itemSubCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-sub-category-delete-popup-extended',
    template: ''
})
export class ItemSubCategoryDeletePopupComponentExtended extends ItemSubCategoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {
        super(activatedRoute, router, modalService);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemSubCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemSubCategoryDeleteDialogComponentExtended as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.itemSubCategory = itemSubCategory;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/item-sub-category', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/item-sub-category', { outlets: { popup: null } }]);
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
