import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from './item-category.service';

@Component({
    selector: 'jhi-item-category-update',
    templateUrl: './item-category-update.component.html'
})
export class ItemCategoryUpdateComponent implements OnInit {
    itemCategory: IItemCategory;
    isSaving: boolean;

    constructor(protected itemCategoryService: ItemCategoryService, protected activatedRoute: ActivatedRoute) {}

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
