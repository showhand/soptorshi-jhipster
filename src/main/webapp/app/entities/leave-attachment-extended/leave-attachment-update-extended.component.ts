import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { LeaveAttachmentExtendedService } from './leave-attachment-extended.service';
import { LeaveAttachmentUpdateComponent } from 'app/entities/leave-attachment';
import { DATE_TIME_FORMAT } from 'app/shared';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { AccountService } from 'app/core';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { LeaveApplicationExtendedService } from 'app/entities/leave-application-extended';

@Component({
    selector: 'jhi-leave-attachment-update-extended',
    templateUrl: './leave-attachment-update-extended.component.html'
})
export class LeaveAttachmentUpdateExtendedComponent extends LeaveAttachmentUpdateComponent implements OnInit {
    currentAccount: any;
    employee: IEmployee;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected leaveAttachmentService: LeaveAttachmentExtendedService,
        protected leaveApplicationService: LeaveApplicationExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected accountService: AccountService,
        protected employeeService: EmployeeExtendedService
    ) {
        super(dataUtils, jhiAlertService, leaveAttachmentService, leaveApplicationService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ leaveAttachment }) => {
            this.leaveAttachment = leaveAttachment;
            this.createdOn = this.leaveAttachment.createdOn != null ? this.leaveAttachment.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.leaveAttachment.updatedOn != null ? this.leaveAttachment.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.accountService.identity().then(account => {
            this.employeeService
                .query({
                    'employeeId.equals': account.login
                })
                .subscribe((res: HttpResponse<IEmployee[]>) => {
                    this.employee = res.body[0];
                    if (this.leaveAttachment.id) {
                        this.leaveApplicationService
                            .query({
                                'id.equals': this.leaveAttachment.leaveApplicationId
                            })
                            .pipe(
                                filter((mayBeOk: HttpResponse<ILeaveApplication[]>) => mayBeOk.ok),
                                map((response: HttpResponse<ILeaveApplication[]>) => response.body)
                            )
                            .subscribe(
                                (res: ILeaveApplication[]) => (this.leaveapplications = res),
                                (res: HttpErrorResponse) => this.onError(res.message)
                            );
                    } else {
                        this.leaveApplicationService
                            .query({
                                'employeesId.equals': this.employee.id
                            })
                            .pipe(
                                filter((mayBeOk: HttpResponse<ILeaveApplication[]>) => mayBeOk.ok),
                                map((response: HttpResponse<ILeaveApplication[]>) => response.body)
                            )
                            .subscribe(
                                (res: ILeaveApplication[]) => (this.leaveapplications = res),
                                (res: HttpErrorResponse) => this.onError(res.message)
                            );
                    }
                });
        });
    }
}
