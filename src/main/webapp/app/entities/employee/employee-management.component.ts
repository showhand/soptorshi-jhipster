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
import { AcademicInformationAttachmentService } from 'app/entities/academic-information-attachment';
import { IAcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';
import { filter, map } from 'rxjs/operators';
import { IExperienceInformation } from 'app/shared/model/experience-information.model';
import { IExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';
import { ExperienceInformationService } from 'app/entities/experience-information';
import { ExperienceInformationAttachmentService } from 'app/entities/experience-information-attachment';

@Component({
    selector: 'jhi-employee-management',
    templateUrl: './employee-management.component.html',
    styles: []
})
export class EmployeeManagementComponent implements OnInit, AfterContentInit {
    employee: IEmployee;
    academicInformationList: IAcademicInformation[];
    academicInformationAttachmentList: IAcademicInformationAttachment[];
    experienceInformations: IExperienceInformation[];
    experienceInformationAttachments: IExperienceInformationAttachment[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected activatedRoute: ActivatedRoute,
        protected employeeService: EmployeeService,
        protected designationService: DesignationService,
        protected academicInformationService: AcademicInformationService,
        protected academicInformationAttachmentService: AcademicInformationAttachmentService,
        protected experienceInformationService: ExperienceInformationService,
        protected experienceInformationAttachmentService: ExperienceInformationAttachmentService
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

        this.academicInformationAttachmentService
            .query({
                'employeeId.equals': this.employee.id
            })
            .pipe(
                filter((res: HttpResponse<IAcademicInformationAttachment[]>) => res.ok),
                map((res: HttpResponse<IAcademicInformationAttachment[]>) => res.body)
            )
            .subscribe(
                (res: IAcademicInformationAttachment[]) => {
                    this.academicInformationAttachmentList = res;
                },
                (res: HttpErrorResponse) => this.jhiAlertService.error('Error in fetching academic information attachments')
            );

        this.experienceInformationService
            .query({
                'employeeId.equals': this.employee.id,
                page: 0,
                size: 1000
            })
            .subscribe(
                (res: HttpResponse<IExperienceInformation[]>) => (this.experienceInformations = res.body),
                (error: HttpErrorResponse) => this.jhiAlertService.error('Error in fetching experience information')
            );

        this.experienceInformationAttachmentService
            .query({
                'employeeId.equals': this.employee.id
            })
            .pipe(
                filter((res: HttpResponse<IAcademicInformationAttachment[]>) => res.ok),
                map((res: HttpResponse<IAcademicInformationAttachment[]>) => res.body)
            )
            .subscribe(
                (res: IExperienceInformationAttachment[]) => {
                    this.experienceInformationAttachments = res;
                },
                (res: HttpErrorResponse) => this.jhiAlertService.error('Error in fetching experience information attachments')
            );
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employee }) => {
            this.employee = employee;
            this.loadAll();
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    ngAfterContentInit(): void {}

    previousState() {
        window.history.back();
    }

    enablePersonalInformationEdit() {}

    disablePersonalInformationEdit() {}
}
