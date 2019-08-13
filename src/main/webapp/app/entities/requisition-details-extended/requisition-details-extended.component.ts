import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RequisitionService } from 'app/entities/requisition';
import { IRequisition, RequisitionStatus } from 'app/shared/model/requisition.model';
import { RequisitionDetailsComponent, RequisitionDetailsService } from 'app/entities/requisition-details';

@Component({
    selector: 'jhi-requisition-details-extended',
    templateUrl: './requisition-details-extended.component.html'
})
export class RequisitionDetailsExtendedComponent extends RequisitionDetailsComponent {
    requisitionDetail: IRequisitionDetails;
    requisition: IRequisition;

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
        super(
            requisitionDetailsService,
            parseLinks,
            jhiAlertService,
            accountService,
            activatedRoute,
            router,
            eventManager,
            requisitionService
        );
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

        this.requisitionService.find(this.requisitionDetail.requisitionId).subscribe((res: HttpResponse<IRequisition>) => {
            this.requisition = res.body;
        });

        this.loadAndUpdateRequisition();
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            // this.transition();
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
                    let totalAmount: number = 0;
                    requisitionDetails.forEach((r: IRequisitionDetails) => (totalAmount = totalAmount + r.quantity));
                    this.updateRequisition(totalAmount);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    updateRequisition(totalAmount: number) {
        this.requisitionService.find(this.requisitionDetail.requisitionId).subscribe((res: HttpResponse<IRequisition>) => {
            let requisition = res.body;
            if (requisition.amount != totalAmount) {
                requisition.amount = totalAmount;
                if (requisition.status !== RequisitionStatus.APPROVED_BY_CFO)
                    this.requisitionService
                        .update(requisition)
                        .subscribe((res: HttpResponse<any>) => {}, (res: HttpErrorResponse) => this.jhiAlertService.error(res.error));
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
}
