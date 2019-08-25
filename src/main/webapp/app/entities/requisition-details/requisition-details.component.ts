import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RequisitionDetailsService } from './requisition-details.service';
import { RequisitionService } from 'app/entities/requisition';
import { IRequisition, RequisitionStatus } from 'app/shared/model/requisition.model';

@Component({
    selector: 'jhi-requisition-details',
    templateUrl: './requisition-details.component.html'
})
export class RequisitionDetailsComponent implements OnInit, OnDestroy {
    requisitionDetail: IRequisitionDetails;
    currentAccount: any;
    requisitionDetails: IRequisitionDetails[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    routeData: any;
    links: any;
    totalItems: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;

    constructor(
        protected requisitionDetailsService: RequisitionDetailsService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected requisitionService: RequisitionService
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.requisitionDetailsService
                .search({
                    page: this.page - 1,
                    query: this.currentSearch,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<IRequisitionDetails[]>) => this.paginateRequisitionDetails(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.requisitionDetailsService
            .query({
                'requisitionId.equals': this.requisitionDetail.requisitionId,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IRequisitionDetails[]>) => this.paginateRequisitionDetails(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );

        this.loadAndUpdateRequisition();
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    loadAndUpdateRequisition() {
        this.requisitionDetailsService
            .query({
                'requisitionId.equals': this.requisitionDetail.requisitionId,
                size: 10000
            })
            .subscribe(
                (res: HttpResponse<IRequisitionDetails[]>) => {
                    const requisitionDetails = res.body;
                    let totalAmount = 0;
                    requisitionDetails.forEach((r: IRequisitionDetails) => (totalAmount = totalAmount + r.quantity));
                    this.updateRequisition(totalAmount);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    updateRequisition(totalAmount: number) {
        this.requisitionService.find(this.requisitionDetail.requisitionId).subscribe((res: HttpResponse<IRequisition>) => {
            const requisition = res.body;
            if (requisition.amount !== totalAmount) {
                requisition.amount = totalAmount;
                if (requisition.status !== RequisitionStatus.APPROVED_BY_CFO) {
                    this.requisitionService
                        .update(requisition)
                        .subscribe(
                            (response: HttpResponse<any>) => {},
                            (response: HttpErrorResponse) => this.jhiAlertService.error(response.error)
                        );
                }
            }
        });
    }

    transition() {
        this.router.navigate(['/requisition-details'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                search: this.currentSearch,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    back() {
        window.history.back();
    }

    clear() {
        this.page = 0;
        this.currentSearch = '';
        this.router.navigate([
            '/requisition-details',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.page = 0;
        this.currentSearch = query;
        this.router.navigate([
            '/requisition-details',
            {
                search: this.currentSearch,
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ requisitionDetails }) => {
            this.requisitionDetail = requisitionDetails;
            this.loadAll();
        });
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRequisitionDetails();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRequisitionDetails) {
        return item.id;
    }

    registerChangeInRequisitionDetails() {
        this.eventSubscriber = this.eventManager.subscribe('requisitionDetailsListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateRequisitionDetails(data: IRequisitionDetails[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.requisitionDetails = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
