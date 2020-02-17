import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { LeaveApplicationExtendedService } from './leave-application-extended.service';
import { LeaveApplicationComponent } from 'app/entities/leave-application';

@Component({
    selector: 'jhi-leave-application-extended',
    templateUrl: './leave-application-extended.component.html'
})
export class LeaveApplicationExtendedComponent extends LeaveApplicationComponent {
    constructor(
        protected leaveApplicationService: LeaveApplicationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(leaveApplicationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
}
