import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { SupplyAreaWiseAccumulationExtendedService } from './supply-area-wise-accumulation-extended.service';
import { SupplyAreaWiseAccumulationComponent } from 'app/entities/supply-area-wise-accumulation';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ISupplyAreaWiseAccumulation } from 'app/shared/model/supply-area-wise-accumulation.model';
import { Employee } from 'app/shared/model/employee.model';
import { EmployeeExtendedService } from 'app/entities/employee-extended';
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';
import { filter, map } from 'rxjs/operators';
import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { Moment } from 'moment';
import { DATE_FORMAT } from 'app/shared';

@Component({
    selector: 'jhi-supply-area-wise-accumulation-extended',
    templateUrl: './supply-area-wise-accumulation-extended.component.html'
})
export class SupplyAreaWiseAccumulationExtendedComponent extends SupplyAreaWiseAccumulationComponent {
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
        protected supplyAreaWiseAccumulationService: SupplyAreaWiseAccumulationExtendedService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected parseLinks: JhiParseLinks,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected employeeService: EmployeeExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService
    ) {
        super(supplyAreaWiseAccumulationService, jhiAlertService, eventManager, parseLinks, activatedRoute, accountService);
    }

    loadAll() {
        if (this.hasAdminAuthority || this.hasScmAdminAuthority) {
            this.supplyAreaWiseAccumulationService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort()
                })
                .subscribe(
                    (res: HttpResponse<ISupplyAreaWiseAccumulation[]>) => this.paginateSupplyAreaWiseAccumulations(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        } else if (this.hasScmAreaManagerAuthority) {
            this.supplyAreaWiseAccumulationService
                .query({
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.sort(),
                    'supplyZoneId.equals': this.currentAreaManager[0].supplyZoneId,
                    'supplyAreaId.equals': this.currentAreaManager[0].supplyAreaId,
                    'supplyAreaManagerId.equals': this.currentAreaManager[0].id
                })
                .subscribe(
                    (res: HttpResponse<ISupplyAreaWiseAccumulation[]>) => this.paginateSupplyAreaWiseAccumulations(res.body, res.headers),
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
        this.registerChangeInSupplyAreaWiseAccumulations();
    }

    downloadReport() {
        if (this.dateOfOrderFrom && this.dateOfOrderTo) {
            this.supplyAreaWiseAccumulationService.downloadReport(
                this.dateOfOrderFrom.format(DATE_FORMAT),
                this.dateOfOrderTo.format(DATE_FORMAT)
            );
        }
    }
}
