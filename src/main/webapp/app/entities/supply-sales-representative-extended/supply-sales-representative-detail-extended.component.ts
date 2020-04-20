import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplySalesRepresentativeDetailComponent } from 'app/entities/supply-sales-representative';

@Component({
    selector: 'jhi-supply-sales-representative-detail-extended',
    templateUrl: './supply-sales-representative-detail-extended.component.html'
})
export class SupplySalesRepresentativeDetailExtendedComponent extends SupplySalesRepresentativeDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
