import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';

@Component({
    selector: 'jhi-item-sub-category-detail',
    templateUrl: './item-sub-category-detail.component.html'
})
export class ItemSubCategoryDetailComponent implements OnInit {
    itemSubCategory: IItemSubCategory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemSubCategory }) => {
            this.itemSubCategory = itemSubCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
