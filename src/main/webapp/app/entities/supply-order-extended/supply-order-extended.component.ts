import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { Account, AccountService } from 'app/core';
import { SupplyOrderExtendedService } from './supply-order-extended.service';
import { SupplyOrderComponent } from 'app/entities/supply-order';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyOrder } from 'app/shared/model/supply-order.model';
import { Employee } from 'app/shared/model/employee.model';
import { ISupplyZoneManager, SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { ISupplyAreaManager, SupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';

@Component({
    selector: 'jhi-supply-order-extended',
    templateUrl: './supply-order-extended.component.html'
})
export class SupplyOrderExtendedComponent extends SupplyOrderComponent implements OnInit {
    currentAccount: Account;
    currentEmployee: Employee[];
    currentZoneManager: SupplyZoneManager[];
    currentAreaManager: SupplyAreaManager[];
    hasScmAreaManagerAuthority: boolean = false;
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
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected employeeService: EmployeeExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService
    ) {
        super(supplyOrderService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        if (this.hasAdminAuthority || this.hasScmAdminAuthority) {
            this.supplyOrderService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ISupplyOrder[]>) => this.paginateSupplyOrders(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.hasScmZoneManagerAuthority) {
            this.supplyOrderService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyZoneId.equals': this.currentZoneManager[0].supplyZoneId
                })
                .subscribe(
                    (res: HttpResponse<ISupplyOrder[]>) => this.paginateSupplyOrders(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.hasScmAreaManagerAuthority) {
            this.supplyOrderService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyZoneId.equals': this.currentAreaManager[0].supplyZoneId,
                    'supplyAreaId.equals': this.currentAreaManager[0].supplyAreaId,
                    'supplyAreaManagerId.equals': this.currentAreaManager[0].id
                })
                .subscribe(
                    (res: HttpResponse<ISupplyOrder[]>) => this.paginateSupplyOrders(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
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
                        this.hasScmAreaManagerAuthority = this.accountService.hasAnyAuthority(['ROLE_SCM_AREA_MANAGER']);
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
        this.registerChangeInSupplyOrders();
    }
}
