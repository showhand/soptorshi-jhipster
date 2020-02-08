import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LeaveTypeDetailComponent } from 'app/entities/leave-type';

@Component({
    selector: 'jhi-leave-type-detail-extended',
    templateUrl: './leave-type-detail-extended.component.html'
})
export class LeaveTypeDetailExtendedComponent extends LeaveTypeDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
