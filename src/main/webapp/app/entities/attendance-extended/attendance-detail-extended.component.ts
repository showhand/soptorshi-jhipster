import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttendance } from 'app/shared/model/attendance.model';

@Component({
    selector: 'jhi-attendance-detail-extended',
    templateUrl: './attendance-detail-extended.component.html'
})
export class AttendanceDetailExtendedComponent implements OnInit {
    attendance: IAttendance;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ attendance }) => {
            this.attendance = attendance;
        });
    }

    previousState() {
        window.history.back();
    }
}
