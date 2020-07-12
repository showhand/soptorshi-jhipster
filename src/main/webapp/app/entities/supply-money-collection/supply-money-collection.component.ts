import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';

import { ISupplyMoneyCollection } from 'app/shared/model/supply-money-collection.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { SupplyMoneyCollectionService } from './supply-money-collection.service';
import { Employee } from 'app/shared/model/employee.model';
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { filter, map } from 'rxjs/operators';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';

@Component({
    selector: 'jhi-supply-money-collection',
    templateUrl: './supply-money-collection.component.html'
})
export class SupplyMoneyCollectionComponent implements OnInit, OnDestroy {
    supplyMoneyCollections: ISupplyMoneyCollection[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;

    hasScmAreaManagerAuthority: boolean = false;
    hasScmZoneManagerAuthority: boolean = false;
    hasScmAdminAuthority: boolean = false;
    hasAdminAuthority: boolean = false;
    currentEmployee: Employee[];
    currentAreaManager: ISupplyAreaManager[];
    currentZoneManager: ISupplyZoneManager[];
    supplyZoneManagers: ISupplyZoneManager[];
    supplyAreaManagers: ISupplyAreaManager[];

    constructor(
        protected supplyMoneyCollectionService: SupplyMoneyCollectionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService
    ) {
        this.supplyMoneyCollections = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.hasAdminAuthority || this.hasScmAdminAuthority) {
            this.supplyMoneyCollectionService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ISupplyMoneyCollection[]>) => this.paginateSupplyMoneyCollections(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.hasScmAreaManagerAuthority) {
            this.supplyMoneyCollectionService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyZoneId.equals': this.currentAreaManager[0].supplyZoneId,
                    'supplyAreaId.equals': this.currentAreaManager[0].supplyAreaId,
                    'supplyAreaManagerId.equals': this.currentAreaManager[0].id
                })
                .subscribe(
                    (res: HttpResponse<ISupplyMoneyCollection[]>) => this.paginateSupplyMoneyCollections(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    reset() {
        this.page = 0;
        this.supplyMoneyCollections = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.supplyMoneyCollections = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = 'id';
        this.reverse = true;
        this.currentSearch = '';
        this.loadAll();
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.supplyMoneyCollections = [];
        this.links = {
            last: 0
        };
        this.page = 0;
        this.predicate = '_score';
        this.reverse = false;
        this.currentSearch = query;
        this.loadAll();
    }

    ngOnInit() {
        this.supplyZoneManagerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZoneManager[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZoneManager[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyZoneManager[]) => {
                    this.supplyZoneManagers = res;
                    this.supplyAreaManagerService
                        .query()
                        .pipe(
                            filter((mayBeOk: HttpResponse<ISupplyAreaManager[]>) => mayBeOk.ok),
                            map((response: HttpResponse<ISupplyAreaManager[]>) => response.body)
                        )
                        .subscribe(
                            (res: ISupplyAreaManager[]) => {
                                this.supplyAreaManagers = res;
                                this.accountService.identity().then(account => {
                                    this.currentAccount = account;
                                    this.employeeService
                                        .query({
                                            'employeeId.equals': this.currentAccount.login
                                        })
                                        .subscribe(
                                            (res: HttpResponse<Employee[]>) => {
                                                this.currentEmployee = res.body;
                                                this.hasAdminAuthority = this.accountService.hasAnyAuthority(['ROLE_ADMIN']);
                                                this.hasScmAdminAuthority = this.accountService.hasAnyAuthority(['ROLE_SCM_ADMIN']);
                                                this.hasScmAreaManagerAuthority = this.accountService.hasAnyAuthority([
                                                    'ROLE_SCM_AREA_MANAGER'
                                                ]);
                                                this.hasScmZoneManagerAuthority = this.accountService.hasAnyAuthority([
                                                    'ROLE_SCM_ZONE_MANAGER'
                                                ]);

                                                if (this.hasScmZoneManagerAuthority) {
                                                    this.supplyZoneManagerService
                                                        .query({
                                                            'employeeId.equals': this.currentEmployee[0].id
                                                        })
                                                        .subscribe(
                                                            (res: HttpResponse<ISupplyZoneManager[]>) => {
                                                                this.currentZoneManager = res.body;
                                                                this.loadAll();
                                                            },
                                                            (res: HttpErrorResponse) => this.onError(res.message)
                                                        );
                                                } else if (this.hasScmAreaManagerAuthority) {
                                                    this.supplyAreaManagerService
                                                        .query({
                                                            'employeeId.equals': this.currentEmployee[0].id
                                                        })
                                                        .subscribe(
                                                            (res: HttpResponse<ISupplyAreaManager[]>) => {
                                                                this.currentAreaManager = res.body;
                                                                this.loadAll();
                                                            },
                                                            (res: HttpErrorResponse) => this.onError(res.message)
                                                        );
                                                } else {
                                                    this.loadAll();
                                                }
                                            },
                                            (res: HttpErrorResponse) => this.onError(res.message)
                                        );
                                });
                            },
                            (res: HttpErrorResponse) => this.onError(res.message)
                        );
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.registerChangeInSupplyMoneyCollections();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISupplyMoneyCollection) {
        return item.id;
    }

    registerChangeInSupplyMoneyCollections() {
        this.eventSubscriber = this.eventManager.subscribe('supplyMoneyCollectionListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateSupplyMoneyCollections(data: ISupplyMoneyCollection[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.supplyMoneyCollections.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
