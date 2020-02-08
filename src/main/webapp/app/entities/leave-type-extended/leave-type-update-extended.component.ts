import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LeaveTypeExtendedService } from './leave-type-extended.service';
import { LeaveTypeUpdateComponent } from 'app/entities/leave-type';

@Component({
    selector: 'jhi-leave-type-update-extended',
    templateUrl: './leave-type-update-extended.component.html'
})
export class LeaveTypeUpdateExtendedComponent extends LeaveTypeUpdateComponent {
    constructor(protected leaveTypeService: LeaveTypeExtendedService, protected activatedRoute: ActivatedRoute) {
        super(leaveTypeService, activatedRoute);
    }
}
