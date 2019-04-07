import { Component, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { IEmployee } from 'app/shared/model/employee.model';

@Component({
    selector: 'jhi-employee-management',
    templateUrl: './employee-management.component.html',
    styles: []
})
export class EmployeeManagementComponent implements OnInit {
    employee: IEmployee;

    constructor(protected jhiAlertService: JhiAlertService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employee }) => {
            this.employee = employee;
        });
    }
}
