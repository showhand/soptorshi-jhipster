import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeExtendedService } from './employee-extended.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation';
import { IUser, User, UserService } from 'app/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EmployeeUpdateComponent } from 'app/entities/employee';

@Component({
    selector: 'jhi-employee-update',
    templateUrl: './employee-extended-update.component.html'
})
export class EmployeeExtendedUpdateComponent extends EmployeeUpdateComponent implements OnInit {
    employee: IEmployee;
    isSaving: boolean;

    departments: IDepartment[];

    offices: IOffice[];

    designations: IDesignation[];
    managers: IEmployee[];
    birthDateDp: any;
    joiningDateDp: any;
    terminationDateDp: any;
    user: User;
    authorities: any[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected employeeService: EmployeeExtendedService,
        protected departmentService: DepartmentService,
        protected officeService: OfficeService,
        protected designationService: DesignationService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute,
        protected userService: UserService,
        protected modalService: NgbModal,
        protected router: Router
    ) {
        super(
            dataUtils,
            jhiAlertService,
            employeeService,
            departmentService,
            officeService,
            designationService,
            elementRef,
            activatedRoute
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employee }) => {
            this.employee = employee;
        });
        this.employeeService
            .query({ 'designationId.in': '1,2,3,4,5,6,7' })
            .subscribe(
                (res: HttpResponse<IEmployee[]>) => (this.managers = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.departmentService
            .query({ 'employeeId.specified': 'false', size: 1000 })
            .pipe(
                filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepartment[]>) => response.body)
            )
            .subscribe(
                (res: IDepartment[]) => {
                    if (!this.employee.departmentId) {
                        this.departments = res;
                    } else {
                        this.departmentService
                            .find(this.employee.departmentId)
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
        this.officeService
            .query({ 'employeeId.specified': 'false' })
            .pipe(
                filter((mayBeOk: HttpResponse<IOffice[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOffice[]>) => response.body)
            )
            .subscribe(
                (res: IOffice[]) => {
                    if (!this.employee.officeId) {
                        this.offices = res;
                    } else {
                        this.officeService
                            .find(this.employee.officeId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IOffice>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IOffice>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IOffice) => (this.offices = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.designationService
            .query({
                size: 1000
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IDesignation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDesignation[]>) => response.body)
            )
            .subscribe((res: IDesignation[]) => (this.designations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    searchDesignations(event) {
        this.designations = [];
        this.designationService
            .query({
                'name.in': event.query
            })
            .pipe(
                filter((mayBeOk: HttpResponse<IDesignation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDesignation[]>) => response.body)
            )
            .subscribe((res: IDesignation[]) => (this.designations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    activationCheckboxChanged(content) {
        this.user = new User();
        if (this.employee.userAccount) {
            this.modalService.open(content);

            this.userService.find(this.employee.employeeId).subscribe(
                response => {
                    this.user = response.body;
                },
                error => {
                    console.log('In error portion');
                    this.user = new User();
                    this.user.login = this.employee.employeeId;
                    this.user.email = this.employee.email;
                }
            );

            this.userService.authorities().subscribe(authorities => {
                this.authorities = authorities;
            });
        } else {
            this.userService.find(this.employee.employeeId).subscribe(
                user => {
                    this.user = user.body;
                    if (this.user !== undefined) {
                        this.user.activated = false;
                        this.userService.update(this.user).subscribe(response => {
                            this.jhiAlertService.info('User account disabled');
                        });
                    }
                },
                error => {}
            );
        }
    }

    cancelUserCreation() {
        this.modalService.dismissAll();
    }

    saveUser() {
        this.userService.find(this.user.login).subscribe(
            response => {
                const user: IUser = response.body;
                user.activated = this.user.activated;
                user.authorities = this.user.authorities;
                this.userService.update(user).subscribe(
                    res => {
                        this.jhiAlertService.info('User Successfully Updated');
                        this.modalService.dismissAll();
                    },
                    () => {
                        this.jhiAlertService.error('Error in updating user');
                    }
                );
            },
            error => {
                this.userService.create(this.user).subscribe(response => {
                    this.jhiAlertService.info('User Successfully Created');
                    this.modalService.dismissAll();
                });
            }
        );
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
        result.subscribe(
            (res: HttpResponse<IEmployee>) => {
                if (this.employee.id) {
                    this.onSaveSuccess();
                } else {
                    const employee: IEmployee = res.body;
                    this.router.navigate(['/employee', employee.id, 'employee-management']);
                }
            },
            (res: HttpErrorResponse) => this.onSaveError()
        );
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
