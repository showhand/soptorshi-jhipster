import { DesignationWiseAllowanceComponent, DesignationWiseAllowanceService } from 'app/entities/designation-wise-allowance';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'app/core';
import { DesignationService } from 'app/entities/designation';
import { Designation } from 'app/shared/model/designation.model';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IDesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';

@Component({
    selector: 'jhi-designation-wise-allowance-extended',
    templateUrl: './designation-wise-allowance-extended.component.html'
})
export class DesignationWiseAllowanceExtendedComponent extends DesignationWiseAllowanceComponent implements OnInit, OnDestroy {
    designations: Designation[];
    constructor(
        public designationWiseAllowanceService: DesignationWiseAllowanceService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        public designationService: DesignationService
    ) {
        super(
            designationWiseAllowanceService,
            jhiAlertService,
            eventManager,
            parseLinks,
            activatedRoute,
            accountService,
            designationService
        );
    }

    loadAll() {
        this.designationWiseAllowances = [];

        if (this.designationWiseAllowanceService.designationId) {
            this.designationWiseAllowanceService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    'designationId.equals': this.designationWiseAllowanceService.designationId,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IDesignationWiseAllowance[]>) => this.paginateDesignationWiseAllowances(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    fetch() {
        this.loadAll();
    }

    ngOnInit() {
        this.designationService
            .query({
                page: 0,
                size: 200,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<Designation[]>) => {
                    this.designations = res.body;
                    if (this.designationWiseAllowanceService.designationId === undefined)
                        this.designationWiseAllowanceService.designationId = this.designations[0].id;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        if (this.designationWiseAllowanceService.designationId !== undefined) {
            this.loadAll();
        }
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDesignationWiseAllowances();
    }
}
