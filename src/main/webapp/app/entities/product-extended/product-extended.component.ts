import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProductComponent } from 'app/entities/product';

@Component({
    selector: 'jhi-product-extended',
    templateUrl: './product-extended.component.html'
})
export class ProductExtendedComponent extends ProductComponent {}
