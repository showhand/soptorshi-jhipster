import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation';

@Component({
    selector: 'jhi-employee-update',
    templateUrl: './employee-update.component.html'
})
export class EmployeeUpdateComponent implements OnInit {
    employee: IEmployee;
    isSaving: boolean;

    departments: IDepartment[];

    offices: IOffice[];

    designations: IDesignation[];
    birthDateDp: any;
    joiningDateDp: any;
    terminationDateDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected employeeService: EmployeeService,
        protected departmentService: DepartmentService,
        protected officeService: OfficeService,
        protected designationService: DesignationService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employee }) => {
            this.employee = employee;
        });
        this.departmentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepartment[]>) => response.body)
            )
            .subscribe((res: IDepartment[]) => (this.departments = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.officeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOffice[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOffice[]>) => response.body)
            )
            .subscribe((res: IOffice[]) => (this.offices = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.designationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDesignation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDesignation[]>) => response.body)
            )
            .subscribe((res: IDesignation[]) => (this.designations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.employee, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.employee.id !== undefined) {
            this.subscribeToSaveResponse(this.employeeService.update(this.employee));
        } else {
            this.subscribeToSaveResponse(this.employeeService.create(this.employee));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>) {
        result.subscribe((res: HttpResponse<IEmployee>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOfficeById(index: number, item: IOffice) {
        return item.id;
    }

    trackDesignationById(index: number, item: IDesignation) {
        return item.id;
    }
}
