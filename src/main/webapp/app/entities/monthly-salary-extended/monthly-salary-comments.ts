import { SalaryMessagesComponent, SalaryMessagesService } from 'app/entities/salary-messages';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISalaryMessages } from 'app/shared/model/salary-messages.model';

@Component({
    selector: 'jhi-monthly-salary-comments',
    templateUrl: './monthly-salary-comments.html'
})
export class MonthlySalaryComments extends SalaryMessagesComponent implements OnInit, OnDestroy {
    @Input()
    monthlySalaryId: number;

    constructor(
        protected salaryMessagesService: SalaryMessagesService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(salaryMessagesService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
        this.page = 0;
        this.previousPage = 0;
        this.predicate = ['id,asc'];
    }

    loadAll() {
        this.salaryMessagesService
            .query({
                page: this.page - 1,
                size: 100,
                sort: this.predicate,
                'monthlySalaryId.equals': this.monthlySalaryId
            })
            .subscribe(
                (res: HttpResponse<ISalaryMessages[]>) => this.paginateSalaryMessages(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    transition() {
        this.loadAll();
    }
}
