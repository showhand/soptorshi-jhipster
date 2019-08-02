import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryService } from './item-sub-category.service';
import { IItemCategory } from 'app/shared/model/item-category.model';
import { ItemCategoryService } from 'app/entities/item-category';

@Component({
    selector: 'jhi-item-sub-category-update',
    templateUrl: './item-sub-category-update.component.html'
})
export class ItemSubCategoryUpdateComponent implements OnInit {
    itemSubCategory: IItemSubCategory;
    isSaving: boolean;

    itemcategories: IItemCategory[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected itemSubCategoryService: ItemSubCategoryService,
        protected itemCategoryService: ItemCategoryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ itemSubCategory }) => {
            this.itemSubCategory = itemSubCategory;
        });
        this.itemCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IItemCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IItemCategory[]>) => response.body)
            )
            .subscribe((res: IItemCategory[]) => (this.itemcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.itemSubCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.itemSubCategoryService.update(this.itemSubCategory));
        } else {
            this.subscribeToSaveResponse(this.itemSubCategoryService.create(this.itemSubCategory));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemSubCategory>>) {
        result.subscribe((res: HttpResponse<IItemSubCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackItemCategoryById(index: number, item: IItemCategory) {
        return item.id;
    }
}
