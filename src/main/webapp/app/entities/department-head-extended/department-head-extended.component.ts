import { DepartmentHeadComponent, DepartmentHeadService } from 'app/entities/department-head';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Component } from '@angular/core';

@Component({
    selector: 'jhi-department-head-extended',
    templateUrl: './department-head-extended.component.html'
})
export class DepartmentHeadExtendedComponent extends DepartmentHeadComponent {
    constructor(
        protected departmentHeadService: DepartmentHeadService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(departmentHeadService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }
}
