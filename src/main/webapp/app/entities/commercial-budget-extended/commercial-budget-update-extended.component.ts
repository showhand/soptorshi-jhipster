import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommercialBudgetExtendedService } from './commercial-budget-extended.service';
import { CommercialProductInfoService } from 'app/entities/commercial-product-info';
import { CommercialBudgetUpdateComponent } from 'app/entities/commercial-budget';

@Component({
    selector: 'jhi-commercial-budget-update-extended',
    templateUrl: './commercial-budget-update-extended.component.html'
})
export class CommercialBudgetUpdateExtendedComponent extends CommercialBudgetUpdateComponent {
    constructor(
        protected commercialBudgetService: CommercialBudgetExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected commercialProductInfoService: CommercialProductInfoService
    ) {
        super(commercialBudgetService, activatedRoute, commercialProductInfoService);
    }
}
