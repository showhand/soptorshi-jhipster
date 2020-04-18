import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SupplyChallanDetailComponent } from 'app/entities/supply-challan';

@Component({
    selector: 'jhi-supply-challan-detail-extended',
    templateUrl: './supply-challan-detail-extended.component.html'
})
export class SupplyChallanDetailExtendedComponent extends SupplyChallanDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
