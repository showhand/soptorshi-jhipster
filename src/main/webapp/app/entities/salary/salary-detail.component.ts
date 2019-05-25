import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalary } from 'app/shared/model/salary.model';

@Component({
    selector: 'jhi-salary-detail',
    templateUrl: './salary-detail.component.html'
})
export class SalaryDetailComponent implements OnInit {
    salary: ISalary;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salary }) => {
            this.salary = salary;
        });
    }

    previousState() {
        window.history.back();
    }
}
