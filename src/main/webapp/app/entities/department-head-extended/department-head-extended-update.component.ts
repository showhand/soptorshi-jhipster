import { DepartmentHeadService, DepartmentHeadUpdateComponent } from 'app/entities/department-head';
import { JhiAlertService } from 'ng-jhipster';
import { OfficeService } from 'app/entities/office';
import { DepartmentService } from 'app/entities/department';
import { EmployeeService } from 'app/entities/employee';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IOffice } from 'app/shared/model/office.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IEmployee } from 'app/shared/model/employee.model';

@Component({
    selector: 'jhi-department-head-extended-update',
    templateUrl: './department-head-extended-update.component.html'
})
export class DepartmentHeadExtendedUpdateComponent extends DepartmentHeadUpdateComponent implements OnInit {
    selectedEmployee: IEmployee;
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected departmentHeadService: DepartmentHeadService,
        protected officeService: OfficeService,
        protected departmentService: DepartmentService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, departmentHeadService, officeService, departmentService, employeeService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ departmentHead }) => {
            this.departmentHead = departmentHead;
        });
        this.officeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOffice[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOffice[]>) => response.body)
            )
            .subscribe((res: IOffice[]) => (this.offices = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.departmentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepartment[]>) => response.body)
            )
            .subscribe((res: IDepartment[]) => (this.departments = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe(
                (res: IEmployee[]) => {
                    this.employees = res;
                    if (!this.departmentHead.employeeId) {
                        this.employees.forEach((e: IEmployee) => {
                            if (e.id === this.departmentHead.employeeId) {
                                this.selectedEmployee = e;
                            }
                        });
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    save() {
        this.isSaving = true;
        this.departmentHead.employeeId = this.selectedEmployee.id;
        if (this.departmentHead.id !== undefined) {
            this.subscribeToSaveResponse(this.departmentHeadService.update(this.departmentHead));
        } else {
            this.subscribeToSaveResponse(this.departmentHeadService.create(this.departmentHead));
        }
    }

    filterEmployees(event) {
        this.employeeService
            .query({
                'fullName.contains': event.query.toString()
            })
            .subscribe((res: HttpResponse<IEmployee[]>) => {
                this.employees = [];
                this.employees = res.body;
            });
    }
}
