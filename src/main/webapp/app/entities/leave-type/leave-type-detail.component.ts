import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeaveType } from 'app/shared/model/leave-type.model';

@Component({
    selector: 'jhi-leave-type-detail',
    templateUrl: './leave-type-detail.component.html'
})
export class LeaveTypeDetailComponent implements OnInit {
    leaveType: ILeaveType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ leaveType }) => {
            this.leaveType = leaveType;
        });
    }

    previousState() {
        window.history.back();
    }
}
