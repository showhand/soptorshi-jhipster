import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { QuotationDetailsComponent, QuotationDetailsService } from 'app/entities/quotation-details';
import { IQuotationDetails, QuotationDetails } from 'app/shared/model/quotation-details.model';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { QuotationDetailsExtendedComponent } from 'app/entities/quotation-details-extended';
import { IQuotation } from 'app/shared/model/quotation.model';

@Component({
    selector: 'jhi-quotation-details-extended-directive-for-quotation',
    templateUrl: './quotation-details-directive-for-quotation.component.html'
})
export class QuotationDetailsExtendedDirectiveForQuotation extends QuotationDetailsExtendedComponent implements OnInit, OnDestroy {
    @Input()
    quotation: IQuotation;
    quotationDetailsTmp: IQuotationDetails;

    constructor(
        protected quotationDetailsService: QuotationDetailsService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager
    ) {
        super(quotationDetailsService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);
        this.page = 1;
        this.previousPage = 0;
        this.reverse = 'asc';
        this.predicate = 'id';
    }

    loadAll() {
        this.quotationDetailsService
            .query({
                'quotationId.equals': this.quotation.id,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IQuotationDetails[]>) => this.paginateQuotationDetails(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.quotationDetailsTmp = new QuotationDetails();
        this.quotationDetailsTmp.quotationId = this.quotation.id;
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInQuotationDetails();
    }

    transition() {
        this.loadAll();
    }
}
