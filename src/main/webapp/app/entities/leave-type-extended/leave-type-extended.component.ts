import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { LeaveTypeExtendedService } from './leave-type-extended.service';
import { LeaveTypeComponent } from 'app/entities/leave-type';

@Component({
    selector: 'jhi-leave-type-extended',
    templateUrl: './leave-type-extended.component.html'
})
export class LeaveTypeExtendedComponent extends LeaveTypeComponent {
    constructor(
        protected leaveTypeService: LeaveTypeExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(leaveTypeService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    generateReport() {
        this.leaveTypeService.generateReport();
    }
}
