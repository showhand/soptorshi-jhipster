import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBudgetAllocation } from 'app/shared/model/budget-allocation.model';

@Component({
    selector: 'jhi-budget-allocation-detail',
    templateUrl: './budget-allocation-detail.component.html'
})
export class BudgetAllocationDetailComponent implements OnInit {
    budgetAllocation: IBudgetAllocation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ budgetAllocation }) => {
            this.budgetAllocation = budgetAllocation;
        });
    }

    previousState() {
        window.history.back();
    }
}
