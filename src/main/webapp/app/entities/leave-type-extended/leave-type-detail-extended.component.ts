import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeDetailComponent } from 'app/entities/leave-type';

@Component({
    selector: 'jhi-leave-type-detail-extended',
    templateUrl: './leave-type-detail-extended.component.html'
})
export class LeaveTypeDetailExtendedComponent extends LeaveTypeDetailComponent implements OnInit {
    leaveType: ILeaveType;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveType }) => {
            this.leaveType = leaveType;
        });
    }

    previousState() {
        window.history.back();
    }
}
