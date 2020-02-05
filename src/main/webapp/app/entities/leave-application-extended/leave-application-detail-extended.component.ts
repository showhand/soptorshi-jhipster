import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LeaveApplicationDetailComponent } from 'app/entities/leave-application';

@Component({
    selector: 'jhi-leave-application-detail-extended',
    templateUrl: './leave-application-detail-extended.component.html'
})
export class LeaveApplicationDetailExtendedComponent extends LeaveApplicationDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
