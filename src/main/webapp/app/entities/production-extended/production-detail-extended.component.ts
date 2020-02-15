import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductionDetailComponent } from 'app/entities/production';

@Component({
    selector: 'jhi-production-detail-extended',
    templateUrl: './production-detail-extended.component.html'
})
export class ProductionDetailExtendedComponent extends ProductionDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
