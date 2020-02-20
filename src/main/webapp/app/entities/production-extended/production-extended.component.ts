import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ProductionExtendedService } from './production-extended.service';
import { ProductionComponent } from 'app/entities/production';
import { RequisitionExtendedService } from 'app/entities/requisition-extended/requisition-extended.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IProduction } from 'app/shared/model/production.model';
import { IRequisition } from 'app/shared/model/requisition.model';

@Component({
    selector: 'jhi-production-extended',
    templateUrl: './production-extended.component.html'
})
export class ProductionExtendedComponent extends ProductionComponent implements OnInit {
    requisitions: IRequisition[];

    constructor(
        protected productionService: ProductionExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected requisitionService: RequisitionExtendedService
    ) {
        super(productionService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        if (this.currentSearch) {
            this.productionService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'requisitionsId.equals': this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IProduction[]>) => this.paginateProductions(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        /*this.productionService
            .query({
                page: this.page,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IProduction[]>) => this.paginateProductions(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );*/
    }

    ngOnInit() {
        this.requisitionService
            .query()
            .subscribe(
                (res: HttpResponse<IProduction[]>) => (this.requisitions = res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProductions();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.productions = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }
}
