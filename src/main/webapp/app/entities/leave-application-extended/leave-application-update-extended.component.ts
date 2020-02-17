import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { LeaveApplicationExtendedService } from './leave-application-extended.service';
import { LeaveTypeService } from 'app/entities/leave-type';
import { LeaveApplicationUpdateComponent } from 'app/entities/leave-application';

@Component({
    selector: 'jhi-leave-application-update-extended',
    templateUrl: './leave-application-update-extended.component.html'
})
export class LeaveApplicationUpdateExtendedComponent extends LeaveApplicationUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected leaveApplicationService: LeaveApplicationExtendedService,
        protected leaveTypeService: LeaveTypeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, leaveApplicationService, leaveTypeService, activatedRoute);
    }
}
