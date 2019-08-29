import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { MonthlySalaryComponent, MonthlySalaryService } from 'app/entities/monthly-salary';

@Component({
    selector: 'jhi-monthly-salary-extended',
    templateUrl: './monthly-salary-extended.component.html'
})
export class MonthlySalaryExtendedComponent extends MonthlySalaryComponent implements OnInit, OnDestroy {
    constructor(
        protected monthlySalaryService: MonthlySalaryService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(monthlySalaryService, parseLinks, jhiAlertService, accountService, activatedRoute, router, eventManager);
    }
}
