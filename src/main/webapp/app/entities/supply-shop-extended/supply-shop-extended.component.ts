import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { Account, AccountService } from 'app/core';
import { SupplyShopExtendedService } from './supply-shop-extended.service';
import { SupplyShopComponent } from 'app/entities/supply-shop';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyShop } from 'app/shared/model/supply-shop.model';
import { Employee } from 'app/shared/model/employee.model';
import { ISupplyZoneManager, SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { EmployeeService } from 'app/entities/employee';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';
import { ISupplyAreaManager, SupplyAreaManager } from 'app/shared/model/supply-area-manager.model';

@Component({
    selector: 'jhi-supply-shop-extended',
    templateUrl: './supply-shop-extended.component.html'
})
export class SupplyShopExtendedComponent extends SupplyShopComponent implements OnInit {
    currentAccount: Account;
    currentEmployee: Employee[];
    currentZoneManager: SupplyZoneManager[];
    currentAreaManager: SupplyAreaManager[];
    hasScmAreaManagerAuthority: boolean = false;
    hasScmZoneManagerAuthority: boolean = false;
    hasScmAdminAuthority: boolean = false;
    hasAdminAuthority: boolean = false;

    constructor(
        protected supplyShopService: SupplyShopExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected employeeService: EmployeeService
    ) {
        super(supplyShopService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }
    loadAll() {
        if (this.hasAdminAuthority || this.hasScmAdminAuthority) {
            this.supplyShopService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ISupplyShop[]>) => this.paginateSupplyShops(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.hasScmZoneManagerAuthority) {
            this.supplyShopService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyZoneId.equals': this.currentZoneManager[0].supplyZoneId
                })
                .subscribe(
                    (res: HttpResponse<ISupplyShop[]>) => this.paginateSupplyShops(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.hasScmAreaManagerAuthority) {
            this.supplyShopService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyAreaId.equals': this.currentAreaManager[0].supplyAreaId,
                    'supplyAreaManagerId.equals': this.currentAreaManager[0].id
                })
                .subscribe(
                    (res: HttpResponse<ISupplyShop[]>) => this.paginateSupplyShops(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else {
            this.onError('Access denied!!');
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
        this.registerChangeInSupplyShops();
    }
}
