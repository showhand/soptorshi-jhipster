import { TermsAndConditionsExtendedComponent } from 'app/entities/terms-and-conditions-extended';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ITermsAndConditions, TermsAndConditions } from 'app/shared/model/terms-and-conditions.model';
import { TermsAndConditionsService } from 'app/entities/terms-and-conditions';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';

@Component({
    selector: 'jhi-terms-and-conditions-for-purchase-order',
    templateUrl: './terms-and-conditions-for-purchase-order.html'
})
export class TermsAndConditionsForPurchaseOrder extends TermsAndConditionsExtendedComponent implements OnInit, OnDestroy {
    @Input()
    purchaseOrder: IPurchaseOrder;
    @Input()
    disableEdit: boolean;
    constructor(
        protected termsAndConditionsService: TermsAndConditionsService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(termsAndConditionsService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
        this.itemsPerPage = 100;
        this.page = 1;
        this.previousPage = 0;
        this.reverse = 'asc';
        this.predicate = 'id';
    }

    loadAll() {
        this.termsAndConditionsService
            .query({
                'purchaseOrderId.equals': this.purchaseOrder.id,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ITermsAndConditions[]>) => this.paginateTermsAndConditions(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.termsAndCondition = new TermsAndConditions();
        this.termsAndCondition.purchaseOrderId = this.purchaseOrder.id;
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTermsAndConditions();
    }
}
