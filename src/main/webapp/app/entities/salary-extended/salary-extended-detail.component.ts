import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalary } from 'app/shared/model/salary.model';
import { SalaryDetailComponent } from 'app/entities/salary';

@Component({
    selector: 'jhi-salary-detail',
    templateUrl: './salary-extended-detail.component.html'
})
export class SalaryExtendedDetailComponent extends SalaryDetailComponent implements OnInit {
    salary: ISalary;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salary }) => {
            this.salary = salary;
        });
    }

    previousState() {
        window.history.back();
    }
}
