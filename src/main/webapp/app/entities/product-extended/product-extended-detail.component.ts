import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProduct } from 'app/shared/model/product.model';
import { ProductDetailComponent } from 'app/entities/product';

@Component({
    selector: 'jhi-product-extended-detail',
    templateUrl: './product-extended-detail.component.html'
})
export class ProductExtendedDetailComponent extends ProductDetailComponent {}
