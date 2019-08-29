import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryDetailComponent } from 'app/entities/monthly-salary';

@Component({
    selector: 'jhi-monthly-salary-extended-detail',
    templateUrl: './monthly-salary-extended-detail.component.html'
})
export class MonthlySalaryExtendedDetailComponent extends MonthlySalaryDetailComponent implements OnInit {
    monthlySalary: IMonthlySalary;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlySalary }) => {
            this.monthlySalary = monthlySalary;
        });
    }

    previousState() {
        window.history.back();
    }
}
