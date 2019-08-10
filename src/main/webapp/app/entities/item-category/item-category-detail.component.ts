import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemCategory } from 'app/shared/model/item-category.model';

@Component({
    selector: 'jhi-item-category-detail',
    templateUrl: './item-category-detail.component.html'
})
export class ItemCategoryDetailComponent implements OnInit {
    itemCategory: IItemCategory;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemCategory }) => {
            this.itemCategory = itemCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
