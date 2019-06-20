import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService, JhiParseLinks } from 'ng-jhipster';

import { IPayrollManagement, PayrollManagement } from 'app/shared/model/payroll-management.model';
import { AccountService } from 'app/core';
import { PayrollManagementService } from './payroll-management.service';
import { Office } from 'app/shared/model/office.model';
import { Designation } from 'app/shared/model/designation.model';
import { OfficeService } from 'app/entities/office';
import { DesignationService } from 'app/entities/designation';
import { EmployeeService } from 'app/entities/employee';
import { Employee, IEmployee } from 'app/shared/model/employee.model';
import { MonthlySalaryService } from 'app/entities/monthly-salary';
import { IMonthlySalary, MonthlySalary } from 'app/shared/model/monthly-salary.model';
import { SalaryService } from 'app/entities/salary';

@Component({
    selector: 'jhi-payroll-management',
    templateUrl: './payroll-management.component.html'
})
export class PayrollManagementComponent implements OnInit, OnDestroy {
    year: number;
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    payrollManagement: PayrollManagement;
    officeList: Office[];
    designationList: Designation[];
    predicate: any;
    reverse: any;
    page: number;
    itemPerPage: number;
    employees: Employee[];
    monthlySalaries: MonthlySalary[];
    monthlySalaryMapWithEmployeeId: any;
    links: any;
    totalItems: any;
    payrollGenerated: boolean;

    constructor(
        protected payrollManagementService: PayrollManagementService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected officeService: OfficeService,
        protected designationService: DesignationService,
        protected employeeService: EmployeeService,
        protected parseLinks: JhiParseLinks,
        protected monthlySalaryService: MonthlySalaryService,
        protected salaryService: SalaryService
    ) {
        this.predicate = 'id';
        this.reverse = false;
        this.payrollManagement = new PayrollManagement();
        this.page = 1;
        this.itemPerPage = 15;
    }

    fetch() {
        this.monthlySalaryMapWithEmployeeId = {};
        this.employeeService
            .query({
                page: this.page - 1,
                size: this.itemPerPage,
                'designationId.equals': this.payrollManagement.designationId,
                'officeId.equals': this.payrollManagement.officeId,
                'employeeStatus.equals': 'ACTIVE'
            })
            .subscribe(
                (res: HttpResponse<IEmployee[]>) => {
                    this.paginateEmployees(res.body, res.headers);
                    this.getMonthlySalaries(res.body);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    public generatePayroll() {
        this.salaryService
            .generatePayroll(
                this.payrollManagement.officeId,
                this.payrollManagement.designationId,
                this.year,
                this.payrollManagement.monthType
            )
            .subscribe((res: any) => {
                this.fetch();
            });
    }

    protected getMonthlySalaries(employees: IEmployee[]) {
        const employeeIds: number[] = [];
        employees.forEach(e => {
            employeeIds.push(e.id);
        });

        this.monthlySalaryService
            .query({
                'year.equals': this.year,
                'month.equals': this.payrollManagement.monthType,
                'employeeId.in': employeeIds
            })
            .subscribe(
                (res: HttpResponse<IMonthlySalary[]>) => {
                    if (res.body.length == 0) {
                        this.payrollGenerated = false;
                    } else {
                        this.payrollGenerated = true;
                        this.monthlySalaryMapWithEmployeeId = {};
                        res.body.forEach(m => {
                            this.monthlySalaryMapWithEmployeeId[m.employeeId] = m;
                        });
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    protected paginateEmployees(data: IEmployee[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.employees = data;
    }

    loadAll() {
        this.officeService
            .query({
                page: 0,
                size: 200,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<Office[]>) => {
                    this.officeList = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.designationService
            .query({
                page: 0,
                size: 200,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<Designation[]>) => {
                    this.designationList = res.body;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        const date = new Date();
        this.year = date.getFullYear();
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPayrollManagements();
    }

    registerChangeInPayrollManagements() {
        this.eventSubscriber = this.eventManager.subscribe('payrollManagementListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
