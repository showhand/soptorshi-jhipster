import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyOrderExtendedService } from './supply-order-extended.service';
import { ISupplyOrder, SupplyOrderStatus } from 'app/shared/model/supply-order.model';
import { Subscription } from 'rxjs';
import { DATE_FORMAT, ITEMS_PER_PAGE } from 'app/shared';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import * as moment from 'moment';
import { Moment } from 'moment';
import { SupplyOrderDetailsService } from 'app/entities/supply-order-details';
import { ISupplyOrderDetails } from 'app/shared/model/supply-order-details.model';
import { IAccumulate } from 'app/entities/supply-order-extended/Accumulate';
import { Employee } from 'app/shared/model/employee.model';
import { ISupplyZoneManager, SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';

@Component({
    selector: 'accumulate-order',
    templateUrl: './accumulate-order.component.html'
})
export class AccumulateOrderComponent {
    supplyOrders: ISupplyOrder[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    reverse: any;
    totalItems: number;
    currentSearch: string;
    distinctDates: Moment[];
    supplyOrderStatuses: SupplyOrderStatus;
    fromDate: Moment;
    toDate: Moment;
    status: SupplyOrderStatus;
    accumulateOrders: IAccumulate[];
    refNo: string;

    currentEmployee: Employee[];
    currentZoneManager: SupplyZoneManager[];
    currentAreaManager: SupplyAreaManager[];
    hasScmZoneManagerAuthority: boolean = false;
    hasScmAdminAuthority: boolean = false;
    hasAdminAuthority: boolean = false;

    constructor(
        protected supplyOrderService: SupplyOrderExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected supplyOrderDetailsService: SupplyOrderDetailsService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected employeeService: EmployeeExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService
    ) {
        this.supplyOrders = [];
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
            if (this.fromDate && this.toDate && this.status) {
                this.supplyOrderService
                    .query({
                        page: this.page,
                        size: this.itemsPerPage,
                        sort: this.sort(),
                        'dateOfOrder.greaterOrEqualThan': moment(this.fromDate).format(DATE_FORMAT),
                        'dateOfOrder.lessOrEqualThan': moment(this.toDate).format(DATE_FORMAT),
                        'supplyOrderStatus.equals': this.status
                    })
                    .subscribe(
                        (res: HttpResponse<ISupplyOrder[]>) => this.paginateSupplyOrders(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            } else if (this.fromDate && this.toDate) {
                this.supplyOrderService
                    .query({
                        page: this.page,
                        size: this.itemsPerPage,
                        sort: this.sort(),
                        'dateOfOrder.greaterOrEqualThan': moment(this.fromDate).format(DATE_FORMAT),
                        'dateOfOrder.lessOrEqualThan': moment(this.toDate).format(DATE_FORMAT)
                    })
                    .subscribe(
                        (res: HttpResponse<ISupplyOrder[]>) => this.paginateSupplyOrders(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            } else if (this.status) {
                this.supplyOrderService
                    .query({
                        page: this.page,
                        size: this.itemsPerPage,
                        sort: this.sort(),
                        'supplyOrderStatus.equals': this.status
                    })
                    .subscribe(
                        (res: HttpResponse<ISupplyOrder[]>) => this.paginateSupplyOrders(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            }
        } else if (this.hasScmZoneManagerAuthority) {
            if (this.fromDate && this.toDate && this.status) {
                this.supplyOrderService
                    .query({
                        page: this.page,
                        size: this.itemsPerPage,
                        sort: this.sort(),
                        'supplyZoneId.equals': this.currentZoneManager[0].supplyZoneId,
                        'dateOfOrder.greaterOrEqualThan': moment(this.fromDate).format(DATE_FORMAT),
                        'dateOfOrder.lessOrEqualThan': moment(this.toDate).format(DATE_FORMAT),
                        'supplyOrderStatus.equals': this.status
                    })
                    .subscribe(
                        (res: HttpResponse<ISupplyOrder[]>) => this.paginateSupplyOrders(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            } else if (this.fromDate && this.toDate) {
                this.supplyOrderService
                    .query({
                        page: this.page,
                        size: this.itemsPerPage,
                        sort: this.sort(),
                        'supplyZoneId.equals': this.currentZoneManager[0].supplyZoneId,
                        'dateOfOrder.greaterOrEqualThan': moment(this.fromDate).format(DATE_FORMAT),
                        'dateOfOrder.lessOrEqualThan': moment(this.toDate).format(DATE_FORMAT)
                    })
                    .subscribe(
                        (res: HttpResponse<ISupplyOrder[]>) => this.paginateSupplyOrders(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            } else if (this.status) {
                this.supplyOrderService
                    .query({
                        page: this.page,
                        size: this.itemsPerPage,
                        sort: this.sort(),
                        'supplyZoneId.equals': this.currentZoneManager[0].supplyZoneId,
                        'supplyOrderStatus.equals': this.status
                    })
                    .subscribe(
                        (res: HttpResponse<ISupplyOrder[]>) => this.paginateSupplyOrders(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            }
        }
    }

    reset() {
        this.page = 0;
        this.supplyOrders = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }

    clear() {
        this.supplyOrders = [];
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
        this.supplyOrders = [];
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
                        this.hasScmZoneManagerAuthority = this.accountService.hasAnyAuthority(['ROLE_SCM_ZONE_MANAGER']);

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
                        } else {
                            this.loadAll();
                        }
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });
        this.registerChangeInSupplyOrders();

        this.supplyOrderService
            .getDistinctSupplyOrderDates()
            .subscribe(
                (res: HttpResponse<Moment[]>) => this.addDistinctSupplyOrderDates(res.body),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    protected addDistinctSupplyOrderDates(data: Moment[]) {
        this.distinctDates = data;
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISupplyOrder) {
        return item.id;
    }

    registerChangeInSupplyOrders() {
        this.eventSubscriber = this.eventManager.subscribe('supplyOrderListModification', response => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    protected paginateSupplyOrders(data: ISupplyOrder[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.supplyOrders.push(data[i]);
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    accumulate() {
        this.accumulateOrders = [];

        const map: string = this.supplyOrders.map(val => val.id).join(',');
        /*'id.in': [map]*/

        this.supplyOrderDetailsService
            .query({
                'supplyOrderId.in': [map]
            })
            .subscribe(
                (res: HttpResponse<ISupplyOrderDetails[]>) => {
                    let orderDetails: ISupplyOrderDetails[] = res.body;

                    let flag: boolean = false;
                    let inc: number = 0;
                    for (let i = 0; i < orderDetails.length; i++) {
                        for (let j = 0; j < this.accumulateOrders.length; j++) {
                            if (this.accumulateOrders[j].productName != undefined) {
                                if (orderDetails[i].productName === this.accumulateOrders[j].productName) {
                                    flag = true;
                                }
                            }
                        }
                        if (flag == true) {
                            flag = false;
                        } else {
                            this.accumulateOrders[inc] = <IAccumulate>{};
                            this.accumulateOrders[inc].productName = orderDetails[i].productName;
                            this.accumulateOrders[inc].quantity = 0;
                            this.accumulateOrders[inc].amount = 0;
                            inc++;
                            flag = false;
                        }
                    }

                    for (let k = 0; k < this.accumulateOrders.length; k++) {
                        for (let m = 0; m < orderDetails.length; m++) {
                            if (this.accumulateOrders[k].productName === orderDetails[m].productName) {
                                this.accumulateOrders[k].quantity = this.accumulateOrders[k].quantity + orderDetails[m].quantity;

                                this.accumulateOrders[k].amount = this.accumulateOrders[k].amount + orderDetails[m].price;
                            }
                        }
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    proceed() {
        if (this.refNo) {
            this.supplyOrderService.updateStatusAndReferenceNo(this.refNo, this.fromDate, this.toDate, this.status).subscribe(
                (res: HttpResponse<number>) => {
                    if (res.body == 1) {
                        this.jhiAlertService.success('Reference Number Has Been Updated!!');
                    } else {
                        this.jhiAlertService.error('Can Not Update Reference Number!!');
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
    }

    download() {
        if (this.refNo) {
            this.supplyOrderService.downloadAccumulatedOrders(this.refNo);
        }
    }
}
