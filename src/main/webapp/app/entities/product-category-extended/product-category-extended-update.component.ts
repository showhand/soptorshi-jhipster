import { Component, OnInit } from '@angular/core';
import { ProductCategoryService, ProductCategoryUpdateComponent } from 'app/entities/product-category';
import { Observable } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { JhiDataUtils } from 'ng-jhipster';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'jhi-product-category-extended-update',
    templateUrl: './product-category-extended-update.component.html'
})
export class ProductCategoryExtendedUpdateComponent extends ProductCategoryUpdateComponent {
    constructor(
        protected dataUtils: JhiDataUtils,
        protected productCategoryService: ProductCategoryService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router
    ) {
        super(dataUtils, productCategoryService, activatedRoute);
    }
    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductCategory>>) {
        result.subscribe(
            (res: HttpResponse<IProductCategory>) => {
                this.productCategory = res.body;
                this.productCategory.productCategoryId = this.productCategory.id;
                this.onSaveSuccess();
            },
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.router.navigate(['/product', this.productCategory.productCategoryId, 'home']);
    }
}
