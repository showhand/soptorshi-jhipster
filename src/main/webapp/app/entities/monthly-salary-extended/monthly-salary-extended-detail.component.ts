import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryDetailComponent } from 'app/entities/monthly-salary';
import { MonthlySalaryExtendedService } from 'app/entities/monthly-salary-extended/monthly-salary-extended.service';
import { JhiAlert, JhiAlertService } from 'ng-jhipster';
import { forkJoin } from 'rxjs';

@Component({
    selector: 'jhi-monthly-salary-extended-detail',
    templateUrl: './monthly-salary-extended-detail.component.html'
})
export class MonthlySalaryExtendedDetailComponent extends MonthlySalaryDetailComponent implements OnInit {
    monthlySalary: IMonthlySalary;
    monthlySalaryId: number;
    totalWorkingDays: number;
    totalPresent: number;
    totalPresentWithoutPay: number;

    constructor(
        protected activatedRoute: ActivatedRoute,
        private monthlySalaryService: MonthlySalaryExtendedService,
        private jhiAlertService: JhiAlertService
    ) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlySalary }) => {
            this.monthlySalary = monthlySalary;
            this.monthlySalaryId = this.monthlySalary.id;

            forkJoin(
                this.monthlySalaryService.getTotalWorkingDays(this.monthlySalary.month, this.monthlySalary.year),
                this.monthlySalaryService.getTotalPresent(this.monthlySalary.employeeId, this.monthlySalary.month, this.monthlySalary.year),
                this.monthlySalaryService.getTotalPresentWithoutPay(
                    this.monthlySalary.employeeId,
                    this.monthlySalary.month,
                    this.monthlySalary.year
                )
            ).subscribe(res => {
                this.totalWorkingDays = res[0].body;
                this.totalPresent = res[1].body;
                this.totalPresent = res[2].body;
            });
        });
    }

    update(): void {
        this.monthlySalaryService.update(this.monthlySalary).subscribe(res => {
            this.jhiAlertService.clear();
            this.jhiAlertService.success('Salary payable successfully updated');
        });
    }

    previousState() {
        window.history.back();
    }
}
