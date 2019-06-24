import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDepartmentHead } from 'app/shared/model/department-head.model';
import { DepartmentHeadService } from './department-head.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office';

@Component({
    selector: 'jhi-department-head-update',
    templateUrl: './department-head-update.component.html'
})
export class DepartmentHeadUpdateComponent implements OnInit {
    departmentHead: IDepartmentHead;
    isSaving: boolean;

    departments: IDepartment[];

    employees: IEmployee[];

    offices: IOffice[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected departmentHeadService: DepartmentHeadService,
        protected departmentService: DepartmentService,
        protected employeeService: EmployeeService,
        protected officeService: OfficeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ departmentHead }) => {
            this.departmentHead = departmentHead;
        });
        this.departmentService
            .query({ 'departmentHeadId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepartment[]>) => response.body)
            )
            .subscribe(
                (res: IDepartment[]) => {
                    if (!this.departmentHead.departmentId) {
                        this.departments = res;
                    } else {
                        this.departmentService
                            .find(this.departmentHead.departmentId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IDepartment>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IDepartment>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IDepartment) => (this.departments = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.employeeService
            .query({ 'departmentHeadId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe(
                (res: IEmployee[]) => {
                    if (!this.departmentHead.employeeId) {
                        this.employees = res;
                    } else {
                        this.employeeService
                            .find(this.departmentHead.employeeId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IEmployee>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IEmployee>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IEmployee) => (this.employees = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.officeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOffice[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOffice[]>) => response.body)
            )
            .subscribe((res: IOffice[]) => (this.offices = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.departmentHead.id !== undefined) {
            this.subscribeToSaveResponse(this.departmentHeadService.update(this.departmentHead));
        } else {
            this.subscribeToSaveResponse(this.departmentHeadService.create(this.departmentHead));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepartmentHead>>) {
        result.subscribe((res: HttpResponse<IDepartmentHead>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackDepartmentById(index: number, item: IDepartment) {
        return item.id;
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }

    trackOfficeById(index: number, item: IOffice) {
        return item.id;
    }
}
