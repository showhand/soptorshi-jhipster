import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from './item-category.service';

@Component({
    selector: 'jhi-item-category-delete-dialog',
    templateUrl: './item-category-delete-dialog.component.html'
})
export class ItemCategoryDeleteDialogComponent {
    itemCategory: IItemCategory;

    constructor(
        protected itemCategoryService: ItemCategoryService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

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
    selector: 'jhi-item-category-delete-popup',
    template: ''
})
export class ItemCategoryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemCategoryDeleteDialogComponent as Component, {
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
