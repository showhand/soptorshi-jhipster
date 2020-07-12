import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { Account, AccountService } from 'app/core';
import { SupplySalesRepresentativeExtendedService } from './supply-sales-representative-extended.service';
import { SupplySalesRepresentativeComponent } from 'app/entities/supply-sales-representative';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ISupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';
import { Employee } from 'app/shared/model/employee.model';
import { ISupplyZoneManager, SupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { ISupplyAreaManager, SupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';
import { filter, map } from 'rxjs/operators';

@Component({
    selector: 'jhi-supply-sales-representative-extended',
    templateUrl: './supply-sales-representative-extended.component.html'
})
export class SupplySalesRepresentativeExtendedComponent extends SupplySalesRepresentativeComponent implements OnInit {
    currentAccount: Account;
    currentEmployee: Employee[];
    currentZoneManager: SupplyZoneManager[];
    currentAreaManager: SupplyAreaManager[];
    supplyZoneManagers: SupplyZoneManager[];
    supplyAreaManagers: SupplyAreaManager[];
    hasScmAreaManagerAuthority: boolean = false;
    hasScmZoneManagerAuthority: boolean = false;
    hasScmAdminAuthority: boolean = false;
    hasAdminAuthority: boolean = false;

    constructor(
        protected supplySalesRepresentativeService: SupplySalesRepresentativeExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected employeeService: EmployeeExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService
    ) {
        super(supplySalesRepresentativeService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        if (this.hasAdminAuthority || this.hasScmAdminAuthority) {
            this.supplySalesRepresentativeService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ISupplySalesRepresentative[]>) => this.paginateSupplySalesRepresentatives(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
        if (this.hasScmZoneManagerAuthority) {
            this.supplySalesRepresentativeService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyZoneId.equals': this.currentZoneManager[0].supplyZoneId
                })
                .subscribe(
                    (res: HttpResponse<ISupplySalesRepresentative[]>) => this.paginateSupplySalesRepresentatives(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
        if (this.hasScmAreaManagerAuthority) {
            this.supplySalesRepresentativeService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyZoneId.equals': this.currentAreaManager[0].supplyZoneId,
                    'supplyAreaId.equals': this.currentAreaManager[0].supplyAreaId,
                    'supplyAreaManagerId.equals': this.currentAreaManager[0].id
                })
                .subscribe(
                    (res: HttpResponse<ISupplySalesRepresentative[]>) => this.paginateSupplySalesRepresentatives(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
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

        this.registerChangeInSupplySalesRepresentatives();
    }

    protected paginateSupplySalesRepresentatives(data: ISupplySalesRepresentative[], headers: HttpHeaders) {
        this.supplySalesRepresentatives = [];
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        for (let i = 0; i < data.length; i++) {
            this.supplySalesRepresentatives.push(data[i]);
        }
    }
}
