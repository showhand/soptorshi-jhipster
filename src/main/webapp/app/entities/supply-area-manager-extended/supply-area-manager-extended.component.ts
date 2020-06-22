import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { Account, AccountService } from 'app/core';
import { SupplyAreaManagerExtendedService } from './supply-area-manager-extended.service';
import { SupplyAreaManagerComponent } from 'app/entities/supply-area-manager';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyAreaManager, SupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { ISupplyZoneManager, SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { Employee } from 'app/shared/model/employee.model';
import { EmployeeExtendedService } from 'app/entities/employee-extended';

@Component({
    selector: 'jhi-supply-area-manager-extended',
    templateUrl: './supply-area-manager-extended.component.html'
})
export class SupplyAreaManagerExtendedComponent extends SupplyAreaManagerComponent implements OnInit {
    currentAccount: Account;
    currentEmployee: Employee[];
    currentZoneManager: SupplyZoneManager[];
    currentAreaManager: SupplyAreaManager[];
    hasScmAreaManagerAuthority: boolean = false;
    hasScmZoneManagerAuthority: boolean = false;
    hasScmAdminAuthority: boolean = false;
    hasAdminAuthority: boolean = false;

    constructor(
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected employeeService: EmployeeExtendedService
    ) {
        super(supplyAreaManagerService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        if (this.hasAdminAuthority || this.hasScmAdminAuthority) {
            this.supplyAreaManagerService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ISupplyAreaManager[]>) => this.paginateSupplyAreaManagers(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.hasScmZoneManagerAuthority) {
            this.supplyAreaManagerService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyZoneId.equals': this.currentZoneManager[0].supplyZoneId,
                    'supplyZoneManagersId.equals': this.currentZoneManager[0].id
                })
                .subscribe(
                    (res: HttpResponse<ISupplyAreaManager[]>) => this.paginateSupplyAreaManagers(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.hasScmAreaManagerAuthority) {
            this.supplyAreaManagerService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyZoneId.equals': this.currentAreaManager[0].supplyZoneId,
                    'supplyAreaId.equals': this.currentAreaManager[0].supplyAreaId,
                    'employeeId.equals': this.currentAreaManager[0].employeeId
                })
                .subscribe(
                    (res: HttpResponse<ISupplyAreaManager[]>) => this.paginateSupplyAreaManagers(res.body, res.headers),
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
        this.registerChangeInSupplyAreaManagers();
    }
}
