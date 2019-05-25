import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';

@Component({
    selector: 'jhi-monthly-salary-detail',
    templateUrl: './monthly-salary-detail.component.html'
})
export class MonthlySalaryDetailComponent implements OnInit {
    monthlySalary: IMonthlySalary;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlySalary }) => {
            this.monthlySalary = monthlySalary;
        });
    }

    previousState() {
        window.history.back();
    }
}
