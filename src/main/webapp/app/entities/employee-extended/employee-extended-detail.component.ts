import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmployee } from 'app/shared/model/employee.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { IOffice } from 'app/shared/model/office.model';
import { DepartmentService } from 'app/entities/department';
import { DesignationService } from 'app/entities/designation';
import { OfficeService } from 'app/entities/office';
import { HttpResponse } from '@angular/common/http';
import { EmployeeDetailComponent } from 'app/entities/employee';

@Component({
    selector: 'jhi-employee-detail',
    templateUrl: './employee-extended-detail.component.html'
})
export class EmployeeExtendedDetailComponent extends EmployeeDetailComponent implements OnInit {
    employee: IEmployee;
    department: IDepartment;
    designation: IDesignation;
    office: IOffice;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected activatedRoute: ActivatedRoute,
        protected departmentService: DepartmentService,
        protected designationService: DesignationService,
        protected officeService: OfficeService
    ) {
        super(dataUtils, activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employee }) => {
            this.employee = employee;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
