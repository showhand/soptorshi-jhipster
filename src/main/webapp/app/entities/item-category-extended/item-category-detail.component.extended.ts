import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryDetailComponent } from 'app/entities/item-category';

@Component({
    selector: 'jhi-item-category-detail-extended',
    templateUrl: './item-category-detail.component.extended.html'
})
export class ItemCategoryDetailComponentExtended extends ItemCategoryDetailComponent implements OnInit {
    itemCategory: IItemCategory;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemCategory }) => {
            this.itemCategory = itemCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
