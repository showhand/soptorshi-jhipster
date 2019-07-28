import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProductCategoryComponent } from 'app/entities/product-category';

@Component({
    selector: 'jhi-product-category-extended',
    templateUrl: './product-category-extended.component.html'
})
export class ProductCategoryExtendedComponent extends ProductCategoryComponent {}
