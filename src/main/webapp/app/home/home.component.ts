import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';
import { Employee, IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Department } from 'app/shared/model/department.model';
import { Designation } from 'app/shared/model/designation.model';
import { DepartmentService } from 'app/entities/department';
import { DesignationService } from 'app/entities/designation';
import { filter, map } from 'rxjs/operators';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    employee: Employee;
    department: Department;
    designation: Designation;

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private employeeService: EmployeeService,
        private jhiAlertService: JhiAlertService,
        private departmentService: DepartmentService,
        private designationService: DesignationService
    ) {}

    fetchLoggedEmployeeInformation() {
        this.employeeService
            .query({
                'employeeId.equals': this.account.login,
                page: 0,
                size: 1
            })
            .subscribe(
                (res: HttpResponse<IEmployee[]>) => {
                    this.employee = res.body[0];
                },
                (res: HttpErrorResponse) => this.jhiAlertService.error('Error in fetching logged employee information')
            );
    }

    fetchAdditionalInformation() {
        this.departmentService.find(this.employee.departmentId).pipe(
            filter((response: HttpResponse<Department>) => response.ok),
            map((response: HttpResponse<Employee>) => (this.department = response.body))
        );

        this.designationService.find(this.employee.designationId).pipe(
            filter((response: HttpResponse<Designation>) => response.ok),
            map((response: HttpResponse<Designation>) => (this.designation = response.body))
        );
    }

    ngOnInit() {
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
