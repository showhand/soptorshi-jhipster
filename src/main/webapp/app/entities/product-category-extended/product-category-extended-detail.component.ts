import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryDetailComponent } from 'app/entities/product-category';

@Component({
    selector: 'jhi-product-category-extended-detail',
    templateUrl: './product-category-extended-detail.component.html'
})
export class ProductCategoryExtendedDetailComponent extends ProductCategoryDetailComponent {}
