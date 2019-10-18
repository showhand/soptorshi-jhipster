import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryDetailComponent } from '../item-sub-category';

@Component({
    selector: 'jhi-item-sub-category-detail-extended',
    templateUrl: './item-sub-category-detail.component.extended.html'
})
export class ItemSubCategoryDetailComponentExtended extends ItemSubCategoryDetailComponent implements OnInit {
    itemSubCategory: IItemSubCategory;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemSubCategory }) => {
            this.itemSubCategory = itemSubCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
