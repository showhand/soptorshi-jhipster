import { ProductComponent, ProductService } from 'app/entities/product';
import { Component } from '@angular/core';
import { IProductCategory, ProductCategory } from 'app/shared/model/product-category.model';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductCategoryService } from 'app/entities/product-category';
import { IProduct } from 'app/shared/model/product.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-product-extended-category-wise',
    templateUrl: './product-extended-category-wise.component.html'
})
export class ProductExtendedCategoryWiseComponent extends ProductComponent {
    productCategory: IProductCategory;
    productCategoryId: number;
    product: IProduct;

    constructor(
        protected productService: ProductService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected productCategoryService: ProductCategoryService
    ) {
        super(productService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
        this.activatedRoute.data.subscribe(({ product }) => {
            this.product = product;
            this.productCategoryId = this.product.productCategoryId;
        });
    }

    loadAll() {
        if (this.currentSearch) {
            this.productService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IProduct[]>) => this.paginateProducts(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.productService
            .query({
                'productCategoryId.equals': this.productCategoryId,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IProduct[]>) => this.paginateProducts(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.productCategoryService
            .find(this.productCategoryId)
            .subscribe(
                (res: HttpResponse<IProductCategory>) => (this.productCategory = res.body),
                (res: HttpErrorResponse) => this.jhiAlertService.error('Error in fetching product category data')
            );
    }

    previousState() {
        window.history.back();
    }
}
