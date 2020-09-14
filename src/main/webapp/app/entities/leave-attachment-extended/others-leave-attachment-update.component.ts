import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ILeaveAttachment } from 'app/shared/model/leave-attachment.model';
import { LeaveAttachmentExtendedService } from './leave-attachment-extended.service';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from 'app/entities/leave-application';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { IManager } from 'app/shared/model/manager.model';
import { AccountService } from 'app/core';
import { ManagerService } from 'app/entities/manager';

@Component({
    selector: 'jhi-others-leave-attachment-update',
    templateUrl: './others-leave-attachment-update.component.html'
})
export class OthersLeaveAttachmentUpdateComponent implements OnInit {
    leaveAttachment: ILeaveAttachment;
    isSaving: boolean;

    leaveapplications: ILeaveApplication[];
    createdOn: string;
    updatedOn: string;

    currentAccount: any;
    employee: IEmployee;
    employees: IEmployee[];

    currentEmployee: IEmployee;
    employeesUnderSupervisor: IManager[];

    currentSearchAsEmployee: IEmployee;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected leaveAttachmentService: LeaveAttachmentExtendedService,
        protected leaveApplicationService: LeaveApplicationService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeExtendedService,
        protected managerService: ManagerService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ leaveAttachment }) => {
            this.leaveAttachment = leaveAttachment;
            this.createdOn = this.leaveAttachment.createdOn != null ? this.leaveAttachment.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.leaveAttachment.updatedOn != null ? this.leaveAttachment.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.leaveApplicationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ILeaveApplication[]>) => mayBeOk.ok),
                map((response: HttpResponse<ILeaveApplication[]>) => response.body)
            )
            .subscribe((res: ILeaveApplication[]) => (this.leaveapplications = res), (res: HttpErrorResponse) => this.onError(res.message));

        this.accountService.identity().then(account => {
            this.currentAccount = account;
            if (this.accountService.hasAnyAuthority(['ROLE_ADMIN']) || this.accountService.hasAnyAuthority(['ROLE_LEAVE_ADMIN'])) {
                this.employeeService
                    .query()
                    .pipe(
                        filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                        map((response: HttpResponse<IEmployee[]>) => response.body)
                    )
                    .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
            } else {
                this.employeeService
                    .query({
                        'employeeId.equals': this.currentAccount.login
                    })
                    .subscribe(
                        (res: HttpResponse<IEmployee[]>) => {
                            this.currentEmployee = res.body[0];
                            this.managerService
                                .query({
                                    'employeeId.equals': this.currentEmployee.id
                                })
                                .subscribe(
                                    (res: HttpResponse<IManager[]>) => {
                                        this.employeesUnderSupervisor = res.body;
                                        const map: string = this.employeesUnderSupervisor.map(val => val.parentEmployeeId).join(',');
                                        this.employeeService
                                            .query({
                                                'id.in': [map]
                                            })
                                            .subscribe(
                                                (res: HttpResponse<IEmployee[]>) => (this.employees = res.body),
                                                (res: HttpErrorResponse) => this.onError(res.message)
                                            );
                                    },
                                    (res: HttpErrorResponse) => this.onError(res.message)
                                );
                        },
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            }
        });
    }

    changeLeaveApplicationId() {
        if (this.employee) {
            this.leaveApplicationService
                .query({
                    'employeesId.equals': this.employee.id
                })
                .subscribe(
                    (ress: HttpResponse<ILeaveApplication[]>) => {
                        this.leaveapplications = ress.body;
                    },
                    (ress: HttpErrorResponse) => this.onError(ress.message)
                );
        }
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.leaveAttachment.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.leaveAttachment.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.leaveAttachment.id !== undefined) {
            this.subscribeToSaveResponse(this.leaveAttachmentService.update(this.leaveAttachment));
        } else {
            this.subscribeToSaveResponse(this.leaveAttachmentService.create(this.leaveAttachment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveAttachment>>) {
        result.subscribe((res: HttpResponse<ILeaveAttachment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLeaveApplicationById(index: number, item: ILeaveApplication) {
        return item.id;
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }
}
