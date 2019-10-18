import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationDetailComponent } from 'app/entities/leave-application';

@Component({
    selector: 'jhi-leave-application-detail-extended',
    templateUrl: './leave-application-detail.component.extended.html'
})
export class LeaveApplicationDetailComponentExtended extends LeaveApplicationDetailComponent implements OnInit {
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
