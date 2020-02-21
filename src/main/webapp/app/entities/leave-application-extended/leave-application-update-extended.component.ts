import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { LeaveApplicationExtendedService } from './leave-application-extended.service';
import { LeaveTypeService } from 'app/entities/leave-type';
import { EmployeeService } from 'app/entities/employee';
import { LeaveApplicationUpdateComponent } from 'app/entities/leave-application';
import { HttpErrorResponse } from '@angular/common/http';
import { DATE_FORMAT } from 'app/shared';

@Component({
    selector: 'jhi-leave-application-update-extended',
    templateUrl: './leave-application-update-extended.component.html'
})
export class LeaveApplicationUpdateExtendedComponent extends LeaveApplicationUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected leaveApplicationService: LeaveApplicationExtendedService,
        protected leaveTypeService: LeaveTypeService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, leaveApplicationService, leaveTypeService, employeeService, activatedRoute);
    }

    calculateDiff() {
        if (this.leaveApplication.fromDate && this.leaveApplication.toDate) {
            this.leaveApplicationService
                .calculateDifference(this.leaveApplication.fromDate.format(DATE_FORMAT), this.leaveApplication.toDate.format(DATE_FORMAT))
                .subscribe(res => (this.leaveApplication.numberOfDays = res.body), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }
}
