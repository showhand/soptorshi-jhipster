import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryService } from './item-sub-category.service';

@Component({
    selector: 'jhi-item-sub-category-delete-dialog',
    templateUrl: './item-sub-category-delete-dialog.component.html'
})
export class ItemSubCategoryDeleteDialogComponent {
    itemSubCategory: IItemSubCategory;

    constructor(
        protected itemSubCategoryService: ItemSubCategoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-item-sub-category-delete-popup',
    template: ''
})
export class ItemSubCategoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemSubCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemSubCategoryDeleteDialogComponent as Component, {
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
