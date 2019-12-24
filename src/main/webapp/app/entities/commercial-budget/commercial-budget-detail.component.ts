import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';

@Component({
    selector: 'jhi-commercial-budget-detail',
    templateUrl: './commercial-budget-detail.component.html'
})
export class CommercialBudgetDetailComponent implements OnInit {
    commercialBudget: ICommercialBudget;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialBudget }) => {
            this.commercialBudget = commercialBudget;
        });
    }

    previousState() {
        window.history.back();
    }
}
