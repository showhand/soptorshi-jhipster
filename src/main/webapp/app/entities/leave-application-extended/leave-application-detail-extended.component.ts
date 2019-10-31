import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationDetailComponent } from 'app/entities/leave-application';

@Component({
    selector: 'jhi-leave-application-detail-extended',
    templateUrl: './leave-application-detail-extended.component.html'
})
export class LeaveApplicationDetailExtendedComponent extends LeaveApplicationDetailComponent implements OnInit {
    leaveApplication: ILeaveApplication;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveApplication }) => {
            this.leaveApplication = leaveApplication;
        });
    }

    previousState() {
        window.history.back();
    }
}
