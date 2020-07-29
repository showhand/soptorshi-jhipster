import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyZoneWiseAccumulationExtendedService } from './supply-zone-wise-accumulation-extended.service';
import { SupplyZoneWiseAccumulationComponent } from 'app/entities/supply-zone-wise-accumulation';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';
import { Employee } from 'app/shared/model/employee.model';
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { filter, map } from 'rxjs/operators';
import { Moment } from 'moment';
import { DATE_FORMAT } from 'app/shared';

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-extended',
    templateUrl: './supply-zone-wise-accumulation-extended.component.html'
})
export class SupplyZoneWiseAccumulationExtendedComponent extends SupplyZoneWiseAccumulationComponent {
    hasScmAreaManagerAuthority: boolean = false;
    hasScmZoneManagerAuthority: boolean = false;
    hasScmAdminAuthority: boolean = false;
    hasAdminAuthority: boolean = false;
    currentEmployee: Employee[];
    currentAreaManager: ISupplyAreaManager[];
    currentZoneManager: ISupplyZoneManager[];
    supplyZoneManagers: ISupplyZoneManager[];
    supplyAreaManagers: ISupplyAreaManager[];

    dateOfOrderFrom: Moment;
    dateOfOrderTo: Moment;

    constructor(
        protected supplyZoneWiseAccumulationService: SupplyZoneWiseAccumulationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService
    ) {
        super(supplyZoneWiseAccumulationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        if (this.hasAdminAuthority || this.hasScmAdminAuthority) {
            this.supplyZoneWiseAccumulationService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ISupplyZoneWiseAccumulation[]>) => this.paginateSupplyZoneWiseAccumulations(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.hasScmZoneManagerAuthority) {
            this.supplyZoneWiseAccumulationService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyZoneId.equals': this.currentZoneManager[0].supplyZoneId,
                    'supplyZoneManagerId.equals': this.currentZoneManager[0].id
                })
                .subscribe(
                    (res: HttpResponse<ISupplyZoneWiseAccumulation[]>) => this.paginateSupplyZoneWiseAccumulations(res.body, res.headers),
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
        this.registerChangeInSupplyZoneWiseAccumulations();
    }

    downloadReport() {
        if (this.dateOfOrderFrom && this.dateOfOrderTo) {
            this.supplyZoneWiseAccumulationService.downloadReport(
                this.dateOfOrderFrom.format(DATE_FORMAT),
                this.dateOfOrderTo.format(DATE_FORMAT)
            );
        }
    }
}
