import { QuotationExtendedComponent } from 'app/entities/quotation-extended';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { IQuotation, Quotation } from 'app/shared/model/quotation.model';
import { IRequisition } from 'app/shared/model/requisition.model';
import { QuotationService } from 'app/entities/quotation';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RequisitionService } from 'app/entities/requisition';

@Component({
    selector: 'jhi-quotation-for-requisition',
    templateUrl: './quotation-for-requisition.component.html'
})
export class QuotationForRequisitionComponent extends QuotationExtendedComponent implements OnInit, OnDestroy {
    @Input()
    requisition: IRequisition;

    constructor(
        protected quotationService: QuotationService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected requisitionService: RequisitionService
    ) {
        super(quotationService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);

        this.itemsPerPage = 100;
        this.page = 1;
        this.previousPage = 0;
        this.reverse = 'asc';
        this.predicate = 'id';
    }

    ngOnInit() {
        this.quotation = new Quotation();
        this.quotation.requisitionId = this.requisition.id;
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInQuotations();
    }
}
