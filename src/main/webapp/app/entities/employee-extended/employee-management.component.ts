/*
import { AfterContentInit, Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ActivatedRoute, Router } from '@angular/router';
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
import { FamilyInformationService } from 'app/entities/family-information';
import { IFamilyInformation } from 'app/shared/model/family-information.model';
import { ReferenceInformationService } from 'app/entities/reference-information';
import { IReferenceInformation } from 'app/shared/model/reference-information.model';
import { ITrainingInformation } from 'app/shared/model/training-information.model';
import { ITrainingInformationAttachment } from 'app/shared/model/training-information-attachment.model';
import { TrainingInformationService } from 'app/entities/training-information';
import { TrainingInformationAttachmentService } from 'app/entities/training-information-attachment';
import { IUser, UserService } from 'app/core';
import { ManagerService } from 'app/entities/manager';
import { IManager } from 'app/shared/model/manager.model';

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
    familyInformations: IFamilyInformation[];
    referenceInformations: IReferenceInformation[];
    trainingInformations: ITrainingInformation[];
    trainingInformationAttachments: ITrainingInformationAttachment[];
    user: IUser;
    managers: IManager[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected activatedRoute: ActivatedRoute,
        protected employeeService: EmployeeService,
        protected designationService: DesignationService,
        protected academicInformationService: AcademicInformationService,
        protected academicInformationAttachmentService: AcademicInformationAttachmentService,
        protected experienceInformationService: ExperienceInformationService,
        protected experienceInformationAttachmentService: ExperienceInformationAttachmentService,
        protected familyInformationService: FamilyInformationService,
        protected referenceInformationService: ReferenceInformationService,
        protected trainingInformationService: TrainingInformationService,
        protected trainingInformationAttachmentService: TrainingInformationAttachmentService,
        protected router: Router,
        protected userService: UserService,
        protected managerService: ManagerService
    ) {}

    loadAll() {
        this.userService
            .find(this.employee.employeeId)
            .subscribe(
                (res: HttpResponse<IUser>) => (this.user = res.body),
                (res: HttpErrorResponse) => this.jhiAlertService.error('Error in fetching user data')
            );

        this.managerService
            .query({
                'parentEmployeeId.equals': this.employee.id
            })
            .subscribe((res: HttpResponse<IManager[]>) => (this.managers = res.body));

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

        this.familyInformationService
            .query({
                'employeeId.equals': this.employee.id
            })
            .subscribe(
                (res: HttpResponse<IFamilyInformation[]>) => (this.familyInformations = res.body),
                (error: HttpErrorResponse) => this.jhiAlertService.error('Error in fetching family information')
            );

        this.referenceInformationService
            .query({
                'employeeId.equals': this.employee.id
            })
            .subscribe(
                (res: HttpResponse<IReferenceInformation[]>) => (this.referenceInformations = res.body),
                (error: HttpErrorResponse) => this.jhiAlertService.error('Error in fetching reference information')
            );

        this.trainingInformationService
            .query({
                'employeeId.equals': this.employee.id,
                page: 0,
                size: 1000
            })
            .subscribe(
                (res: HttpResponse<ITrainingInformation[]>) => (this.trainingInformations = res.body),
                (error: HttpErrorResponse) => this.jhiAlertService.error('Error in fetching training information')
            );

        this.trainingInformationAttachmentService
            .query({
                'employeeId.equals': this.employee.id
            })
            .pipe(
                filter((res: HttpResponse<ITrainingInformationAttachment[]>) => res.ok),
                map((res: HttpResponse<ITrainingInformationAttachment[]>) => res.body)
            )
            .subscribe(
                (res: ITrainingInformationAttachment[]) => {
                    this.trainingInformationAttachments = res;
                },
                (res: HttpErrorResponse) => this.jhiAlertService.error('Error in fetching training information attachments')
            );
    }

    deleteManager(id: number) {
        this.managerService
            .delete(id)
            .subscribe((res: HttpResponse<any>) => this.loadAll(), (res: HttpErrorResponse) => this.jhiAlertService.error(res.message));
    }

    deleteFamilyInformation(id: number) {
        this.familyInformationService
            .delete(id)
            .subscribe(
                (response: HttpResponse<any>) => this.loadAll(),
                (response: HttpErrorResponse) => this.jhiAlertService.error(response.message)
            );
    }

    deleteReferenceInformation(id: number) {
        this.referenceInformationService
            .delete(id)
            .subscribe(
                (response: HttpResponse<any>) => this.loadAll(),
                (response: HttpErrorResponse) => this.jhiAlertService.error(response.message)
            );
    }

    deleteAcademicInformation(id: number) {
        this.academicInformationService
            .delete(id)
            .subscribe(
                (response: HttpResponse<any>) => this.loadAll(),
                (response: HttpErrorResponse) => this.jhiAlertService.error(response.message)
            );
    }

    deleteAcademicInformationAttachment(id: number) {
        this.academicInformationAttachmentService
            .delete(id)
            .subscribe(
                (response: HttpResponse<any>) => this.loadAll(),
                (response: HttpErrorResponse) => this.jhiAlertService.error(response.message)
            );
    }

    deleteExperienceInformation(id: number) {
        this.experienceInformationService
            .delete(id)
            .subscribe(
                (response: HttpResponse<any>) => this.loadAll(),
                (response: HttpErrorResponse) => this.jhiAlertService.error(response.message)
            );
    }

    deleteExperienceInformationAttachment(id: number) {
        this.experienceInformationAttachmentService
            .delete(id)
            .subscribe(
                (response: HttpResponse<any>) => this.loadAll(),
                (response: HttpErrorResponse) => this.jhiAlertService.error(response.message)
            );
    }

    deleteTrainingInformation(id: number) {
        this.trainingInformationService
            .delete(id)
            .subscribe(
                (response: HttpResponse<any>) => this.loadAll(),
                (response: HttpErrorResponse) => this.jhiAlertService.error(response.message)
            );
    }

    deleteTrainingInformationAttachment(id: number) {
        this.trainingInformationAttachmentService
            .delete(id)
            .subscribe(
                (response: HttpResponse<any>) => this.loadAll(),
                (response: HttpErrorResponse) => this.jhiAlertService.error(response.message)
            );
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ employee }) => {
            this.employee = employee;
            this.employee.employeeLongId = this.employee.id;
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
        this.router.navigate(['/employee']);
    }

    enablePersonalInformationEdit() {}

    disablePersonalInformationEdit() {}
}
*/
