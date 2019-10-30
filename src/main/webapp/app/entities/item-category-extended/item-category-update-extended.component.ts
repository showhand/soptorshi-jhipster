import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryExtendedService } from './item-category-extended.service';
import { ItemCategoryUpdateComponent } from 'app/entities/item-category';

@Component({
    selector: 'jhi-item-category-update-extended',
    templateUrl: './item-category-update-extended.component.html'
})
export class ItemCategoryUpdateExtendedComponent extends ItemCategoryUpdateComponent implements OnInit {
    itemCategory: IItemCategory;
    isSaving: boolean;

    constructor(protected itemCategoryService: ItemCategoryExtendedService, protected activatedRoute: ActivatedRoute) {
        super(itemCategoryService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ itemCategory }) => {
            this.itemCategory = itemCategory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.itemCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.itemCategoryService.update(this.itemCategory));
        } else {
            this.subscribeToSaveResponse(this.itemCategoryService.create(this.itemCategory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemCategory>>) {
        result.subscribe((res: HttpResponse<IItemCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
