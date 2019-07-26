import { Component, OnInit } from '@angular/core';
import { ProductCategoryUpdateComponent } from 'app/entities/product-category';

@Component({
    selector: 'jhi-product-category-extended-update',
    templateUrl: './product-category-extended-update.component.html'
})
export class ProductCategoryExtendedUpdateComponent extends ProductCategoryUpdateComponent {}
