import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyChallanExtendedService } from './supply-challan-extended.service';
import { SupplyChallanUpdateComponent } from 'app/entities/supply-challan';
import { SupplyZoneExtendedService } from 'app/entities/supply-zone-extended';
import { SupplyZoneManagerExtendedService } from 'app/entities/supply-zone-manager-extended';
import { SupplyAreaExtendedService } from 'app/entities/supply-area-extended';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';
import { SupplySalesRepresentativeExtendedService } from 'app/entities/supply-sales-representative-extended';
import { SupplyShopExtendedService } from 'app/entities/supply-shop-extended';
import { SupplyOrderExtendedService } from 'app/entities/supply-order-extended';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { ISupplyAreaManager, SupplyAreaManagerStatus } from 'app/shared/model/supply-area-manager.model';
import { ISupplySalesRepresentative, SupplySalesRepresentativeStatus } from 'app/shared/model/supply-sales-representative.model';
import { ISupplyShop } from 'app/shared/model/supply-shop.model';
import { ISupplyOrder } from 'app/shared/model/supply-order.model';
import { DATE_TIME_FORMAT } from 'app/shared';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';

@Component({
    selector: 'jhi-supply-challan-update-extended',
    templateUrl: './supply-challan-update-extended.component.html'
})
export class SupplyChallanUpdateExtendedComponent extends SupplyChallanUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyChallanService: SupplyChallanExtendedService,
        protected supplyZoneService: SupplyZoneExtendedService,
        protected supplyZoneManagerService: SupplyZoneManagerExtendedService,
        protected supplyAreaService: SupplyAreaExtendedService,
        protected supplyAreaManagerService: SupplyAreaManagerExtendedService,
        protected supplySalesRepresentativeService: SupplySalesRepresentativeExtendedService,
        protected supplyShopService: SupplyShopExtendedService,
        protected supplyOrderService: SupplyOrderExtendedService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(
            jhiAlertService,
            supplyChallanService,
            supplyZoneService,
            supplyZoneManagerService,
            supplyAreaService,
            supplyAreaManagerService,
            supplySalesRepresentativeService,
            supplyShopService,
            supplyOrderService,
            activatedRoute
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyChallan }) => {
            this.supplyChallan = supplyChallan;
            this.createdOn = this.supplyChallan.createdOn != null ? this.supplyChallan.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplyChallan.updatedOn != null ? this.supplyChallan.updatedOn.format(DATE_TIME_FORMAT) : null;

            if (!this.supplyChallan.id) {
                this.supplyChallanService
                    .query()
                    .subscribe(
                        (res: HttpResponse<ISupplyOrder[]>) => this.assignChallaNo(res.body, res.headers),
                        (res: HttpErrorResponse) => this.onError(res.message)
                    );
            }
        });
        this.supplyZoneService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZone[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZone[]>) => response.body)
            )
            .subscribe((res: ISupplyZone[]) => (this.supplyzones = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.supplyZoneManagerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZoneManager[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZoneManager[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyZoneManager[]) => (this.supplyzonemanagers = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.supplyAreaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyArea[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyArea[]>) => response.body)
            )
            .subscribe((res: ISupplyArea[]) => (this.supplyareas = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.supplyAreaManagerService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyAreaManager[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyAreaManager[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyAreaManager[]) => (this.supplyareamanagers = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.supplySalesRepresentativeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplySalesRepresentative[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplySalesRepresentative[]>) => response.body)
            )
            .subscribe(
                (res: ISupplySalesRepresentative[]) => (this.supplysalesrepresentatives = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.supplyShopService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyShop[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyShop[]>) => response.body)
            )
            .subscribe((res: ISupplyShop[]) => (this.supplyshops = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.supplyOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyOrder[]>) => response.body)
            )
            .subscribe((res: ISupplyOrder[]) => (this.supplyorders = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    filterZoneManager() {
        if (this.supplyChallan.supplyZoneId) {
            this.supplyZoneManagerService
                .query({
                    'supplyZoneId.equals': this.supplyChallan.supplyZoneId,
                    'status.equals': SupplyZoneManagerStatus.ACTIVE
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyZoneManager[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyZoneManager[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplyZoneManager[]) => {
                        this.supplyzonemanagers = res;
                        this.filterArea();
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    filterArea() {
        if (this.supplyChallan.supplyZoneId) {
            this.supplyAreaService
                .query({
                    'supplyZoneId.equals': this.supplyChallan.supplyZoneId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyArea[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyArea[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplyArea[]) => {
                        this.supplyareas = res;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    filterAreaManager() {
        if (this.supplyChallan.supplyZoneId && this.supplyChallan.supplyAreaId && this.supplyChallan.supplyZoneManagerId) {
            this.supplyAreaManagerService
                .query({
                    'supplyZoneId.equals': this.supplyChallan.supplyZoneId,
                    'supplyAreaId.equals': this.supplyChallan.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyChallan.supplyZoneManagerId,
                    'status.equals': SupplyAreaManagerStatus.ACTIVE
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyAreaManager[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyAreaManager[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplyAreaManager[]) => {
                        this.supplyareamanagers = res;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    filterSalesRepresentative() {
        if (
            this.supplyChallan.supplyZoneId &&
            this.supplyChallan.supplyAreaId &&
            this.supplyChallan.supplyZoneManagerId &&
            this.supplyChallan.supplyAreaManagerId
        ) {
            this.supplySalesRepresentativeService
                .query({
                    'supplyZoneId.equals': this.supplyChallan.supplyZoneId,
                    'supplyAreaId.equals': this.supplyChallan.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyChallan.supplyZoneManagerId,
                    'supplyAreaManagerId.equals': this.supplyChallan.supplyAreaManagerId,
                    'status.equals': SupplySalesRepresentativeStatus.ACTIVE
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplySalesRepresentative[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplySalesRepresentative[]>) => response.body)
                )
                .subscribe(
                    (res: ISupplySalesRepresentative[]) => (this.supplysalesrepresentatives = res),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    filterShop() {
        if (
            this.supplyChallan.supplyZoneId &&
            this.supplyChallan.supplyAreaId &&
            this.supplyChallan.supplyZoneManagerId &&
            this.supplyChallan.supplyAreaManagerId &&
            this.supplyChallan.supplySalesRepresentativeId
        ) {
            this.supplyShopService
                .query({
                    'supplyZoneId.equals': this.supplyChallan.supplyZoneId,
                    'supplyAreaId.equals': this.supplyChallan.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyChallan.supplyZoneManagerId,
                    'supplyAreaManagerId.equals': this.supplyChallan.supplyAreaManagerId,
                    'supplySalesRepresentativeId.equals': this.supplyChallan.supplySalesRepresentativeId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyShop[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyShop[]>) => response.body)
                )
                .subscribe((res: ISupplyShop[]) => (this.supplyshops = res), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    filterOrder() {
        if (
            this.supplyChallan.supplyZoneId &&
            this.supplyChallan.supplyAreaId &&
            this.supplyChallan.supplyZoneManagerId &&
            this.supplyChallan.supplyAreaManagerId &&
            this.supplyChallan.supplySalesRepresentativeId &&
            this.supplyChallan.supplyShopId
        ) {
            this.supplyOrderService
                .query({
                    'supplyZoneId.equals': this.supplyChallan.supplyZoneId,
                    'supplyAreaId.equals': this.supplyChallan.supplyAreaId,
                    'supplyZoneManagerId.equals': this.supplyChallan.supplyZoneManagerId,
                    'supplyAreaManagerId.equals': this.supplyChallan.supplyAreaManagerId,
                    'supplySalesRepresentativeId.equals': this.supplyChallan.supplySalesRepresentativeId,
                    'supplyShopId.equals': this.supplyChallan.supplyShopId
                })
                .pipe(
                    filter((mayBeOk: HttpResponse<ISupplyOrder[]>) => mayBeOk.ok),
                    map((response: HttpResponse<ISupplyOrder[]>) => response.body)
                )
                .subscribe((res: ISupplyOrder[]) => (this.supplyorders = res), (res: HttpErrorResponse) => this.onError(res.message));
        }
    }

    assignChallaNo(data: ISupplyOrder[], headers: HttpHeaders) {
        let today = new Date();
        let year = today
            .getFullYear()
            .toString()
            .substr(-2);
        let month = today.getMonth() + 1;
        let day = today.getDate();
        let hour = today.getHours();
        let minute = today.getMinutes();
        let sec = today.getSeconds();
        let maxId = data.length + 1;
        this.supplyChallan.challanNo =
            'Chln-' +
            year +
            ('0' + month).slice(-2) +
            ('0' + day).slice(-2) +
            ('0' + hour).slice(-2) +
            ('0' + minute).slice(-2) +
            ('0' + sec).slice(-2) +
            '-' +
            this.zeroPadding(maxId, 5);
    }

    zeroPadding(num, places): string {
        const zero = places - num.toString().length + 1;
        return Array(+(zero > 0 && zero)).join('0') + num;
    }
}
