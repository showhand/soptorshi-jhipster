import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { AttendanceDetailComponent } from 'app/entities/attendance';

@Component({
    selector: 'jhi-attendance-detail-extended',
    templateUrl: './attendance-detail-extended.component.html'
})
export class AttendanceDetailExtendedComponent extends AttendanceDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
