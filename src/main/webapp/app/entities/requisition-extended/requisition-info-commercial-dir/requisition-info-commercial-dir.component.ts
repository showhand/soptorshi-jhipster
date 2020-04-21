import { Component, Input, OnInit } from '@angular/core';
import { RequisitionComponent, RequisitionService } from 'app/entities/requisition';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IRequisition, Requisition } from 'app/shared/model/requisition.model';

@Component({
    selector: 'jhi-requisition-info-commercial-dir',
    templateUrl: './requisition-info-commercial-dir.component.html',
    styles: []
})
export class RequisitionInfoCommercialDirComponent extends RequisitionComponent implements OnInit {
    @Input()
    commercialId: number;
    requisition: IRequisition;

    constructor(
        protected requisitionService: RequisitionService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(requisitionService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
    }

    loadAll() {
        this.requisitionService
            .query({
                'commercialId.equals': this.commercialId,
                page: 0,
                size: 200
            })
            .subscribe(
                (res: HttpResponse<IRequisition[]>) => this.paginateRequisitions(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    transition() {
        this.loadAll();
    }

    clear() {
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.requisition = new Requisition();
        this.requisition.commercialId = this.commercialId;
        this.registerChangeInRequisitions();
    }
}
