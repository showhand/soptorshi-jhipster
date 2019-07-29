import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProductCategoryComponent } from 'app/entities/product-category';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { HttpHeaders } from '@angular/common/http';

@Component({
    selector: 'jhi-product-category-extended',
    templateUrl: './product-category-extended.component.html'
})
export class ProductCategoryExtendedComponent extends ProductCategoryComponent {
    protected paginateProductCategories(data: IProductCategory[], headers: HttpHeaders) {
        data.forEach((d: IProductCategory) => {
            d.productCategoryId = d.id;
        });
        super.paginateProductCategories(data, headers);
    }
}
