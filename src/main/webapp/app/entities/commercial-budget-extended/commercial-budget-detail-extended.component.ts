import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CommercialBudgetDetailComponent } from 'app/entities/commercial-budget';

@Component({
    selector: 'jhi-commercial-budget-detail-extended',
    templateUrl: './commercial-budget-detail-extended.component.html'
})
export class CommercialBudgetDetailExtendedComponent extends CommercialBudgetDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
