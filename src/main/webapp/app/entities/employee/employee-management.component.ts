import { AfterContentInit, Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { IEmployee } from 'app/shared/model/employee.model';
import { NgbTab, NgbTabset } from '@ng-bootstrap/ng-bootstrap';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { Table } from 'primeng/table';
import { DesignationService } from 'app/entities/designation';
import { AcademicInformationService } from 'app/entities/academic-information';
import { IAcademicInformation } from 'app/shared/model/academic-information.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-employee-management',
    templateUrl: './employee-management.component.html',
    styles: []
})
export class EmployeeManagementComponent implements OnInit, AfterContentInit {
    employee: IEmployee;
    academicInformationList: IAcademicInformation[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected activatedRoute: ActivatedRoute,
        protected employeeService: EmployeeService,
        protected designationService: DesignationService,
        protected academicInformationService: AcademicInformationService
    ) {}

    loadAll() {
        this.academicInformationService
            .query({
                'employeeId.equals': this.employee.id,
                page: 0,
                size: 1000
            })
            .subscribe(
                (res: HttpResponse<IAcademicInformation[]>) => (this.academicInformationList = res.body),
                (res: HttpErrorResponse) => this.jhiAlertService.error('Error in retrieving academic information data')
            );
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employee }) => {
            this.employee = employee;
            this.loadAll();
        });
    }

    ngAfterContentInit(): void {}

    previousState() {
        window.history.back();
    }

    enablePersonalInformationEdit() {}

    disablePersonalInformationEdit() {}
}
