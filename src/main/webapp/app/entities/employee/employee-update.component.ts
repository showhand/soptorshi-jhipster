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
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation';
import { IUser, User, UserService } from 'app/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-employee-update',
    templateUrl: './employee-update.component.html'
})
export class EmployeeUpdateComponent implements OnInit {
    employee: IEmployee;
    isSaving: boolean;

    departments: IDepartment[];

    designations: IDesignation[];
    birthDateDp: any;
    joiningDateDp: any;
    terminationDateDp: any;
    user: User;
    authorities: any[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected employeeService: EmployeeService,
        protected departmentService: DepartmentService,
        protected designationService: DesignationService,
        protected elementRef: ElementRef,
        protected activatedRoute: ActivatedRoute,
        protected userService: UserService,
        protected modalService: NgbModal
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employee }) => {
            this.employee = employee;
        });
        this.departmentService
            .query({ 'employeeId.specified': 'false' })
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
        this.designationService
            .query()
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
                    if (this.user != undefined) {
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
                let user: IUser = response.body;
                user.activated = this.user.activated;
                user.authorities = this.user.authorities;
                this.userService.update(user).subscribe(
                    response => {
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

    trackDesignationById(index: number, item: IDesignation) {
        return item.id;
    }
}
