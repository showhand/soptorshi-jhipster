import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { SupplyZoneWiseAccumulationExtendedService } from './supply-zone-wise-accumulation-extended.service';
import { SupplyZoneService } from 'app/entities/supply-zone';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';
import { ProductCategoryService } from 'app/entities/product-category';
import { ProductService } from 'app/entities/product';
import { SupplyZoneWiseAccumulationUpdateComponent } from 'app/entities/supply-zone-wise-accumulation';
import { DATE_TIME_FORMAT } from 'app/shared';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { ISupplyZoneManager, SupplyZoneManagerStatus } from 'app/shared/model/supply-zone-manager.model';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { IProduct } from 'app/shared/model/product.model';
import * as moment from 'moment';
import { SupplyAreaWiseAccumulationExtendedService } from 'app/entities/supply-area-wise-accumulation-extended';
import {
    ISupplyAreaWiseAccumulation,
    SupplyAreaWiseAccumulation,
    SupplyAreaWiseAccumulationStatus
} from 'app/shared/model/supply-area-wise-accumulation.model';
import { ISupplyZoneWiseAccumulation, SupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';
import { IScmAreaWiseSelectedProduct, ScmAreaWiseSelectedProduct } from 'app/shared/model/scm-area-wise-selected-product.model';
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyAreaManagerExtendedService } from 'app/entities/supply-area-manager-extended';
import { Observable } from 'rxjs';

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-update-extended',
    templateUrl: './supply-zone-wise-accumulation-update-extended.component.html'
})
export class SupplyZoneWiseAccumulationUpdateExtendedComponent extends SupplyZoneWiseAccumulationUpdateComponent implements OnInit {
    supplyAreaWiseAccumulations: ISupplyAreaWiseAccumulation[];
    accumulatedList: IScmAreaWiseSelectedProduct[];
    selectedAreaWiseAccumulation: ISupplyAreaWiseAccumulation[];
    supplyareamanagers: ISupplyAreaManager[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyZoneWiseAccumulationService: SupplyZoneWiseAccumulationExtendedService,
        protected supplyZoneService: SupplyZoneService,
        protected supplyZoneManagerService: SupplyZoneManagerService,
        protected productCategoryService: ProductCategoryService,
        protected productService: ProductService,
        protected activatedRoute: ActivatedRoute,
        protected supplyAreaWiseAccumulationExtendedService: SupplyAreaWiseAccumulationExtendedService,
        protected supplyAreaManagerExtendedService: SupplyAreaManagerExtendedService
    ) {
        super(
            jhiAlertService,
            supplyZoneWiseAccumulationService,
            supplyZoneService,
            supplyZoneManagerService,
            productCategoryService,
            productService,
            activatedRoute
        );
    }

    ngOnInit() {
        this.isSaving = false;
        this.selectedAreaWiseAccumulation = [];
        this.accumulatedList = [];
        this.activatedRoute.data.subscribe(({ supplyZoneWiseAccumulation }) => {
            this.supplyZoneWiseAccumulation = supplyZoneWiseAccumulation;
            this.createdOn =
                this.supplyZoneWiseAccumulation.createdOn != null
                    ? this.supplyZoneWiseAccumulation.createdOn.format(DATE_TIME_FORMAT)
                    : null;
            this.updatedOn =
                this.supplyZoneWiseAccumulation.updatedOn != null
                    ? this.supplyZoneWiseAccumulation.updatedOn.format(DATE_TIME_FORMAT)
                    : null;
            if (!this.supplyZoneWiseAccumulation.id) {
                this.supplyZoneWiseAccumulationService
                    .query()
                    .subscribe(
                        (res: HttpResponse<ISupplyZoneWiseAccumulation[]>) =>
                            this.assignZoneWiseAccumulationReferenceNo(res.body, res.headers),
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
        this.supplyAreaManagerExtendedService
            .query()
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
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduct[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduct[]>) => response.body)
            )
            .subscribe((res: IProduct[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    save() {
        this.isSaving = true;
        if (!this.supplyZoneWiseAccumulation.id) {
            if (this.selectedAreaWiseAccumulation.length > 0) {
                this.supplyAreaWiseAccumulationExtendedService.bulkUpdate(this.selectedAreaWiseAccumulation).subscribe(
                    (res: HttpResponse<ISupplyAreaWiseAccumulation[]>) => {
                        let supplyZoneWiseAccumulations: SupplyZoneWiseAccumulation[] = [];
                        for (let i = 0; i < this.accumulatedList.length; i++) {
                            let zoneWiseAccumulation: SupplyZoneWiseAccumulation = {};
                            zoneWiseAccumulation.supplyZoneId = this.supplyZoneWiseAccumulation.supplyZoneId;
                            zoneWiseAccumulation.supplyZoneManagerId = this.supplyZoneWiseAccumulation.supplyZoneManagerId;
                            zoneWiseAccumulation.productCategoryId = this.accumulatedList[i].productCategoryId;
                            zoneWiseAccumulation.productId = this.accumulatedList[i].productId;
                            zoneWiseAccumulation.quantity = this.accumulatedList[i].quantity;
                            zoneWiseAccumulation.price = this.accumulatedList[i].price;
                            zoneWiseAccumulation.zoneWiseAccumulationRefNo = this.supplyZoneWiseAccumulation.zoneWiseAccumulationRefNo;
                            zoneWiseAccumulation.status = this.supplyZoneWiseAccumulation.status;
                            zoneWiseAccumulation.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
                            zoneWiseAccumulation.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;

                            supplyZoneWiseAccumulations.push(zoneWiseAccumulation);
                        }
                        this.subscribeToBulkSaveResponse(this.supplyZoneWiseAccumulationService.bulkPost(supplyZoneWiseAccumulations));
                    },
                    (res: HttpErrorResponse) => this.onSaveError()
                );
            } else {
                this.jhiAlertService.error('Nothing Selected!!');
            }
        } else {
            this.subscribeToSaveResponse(this.supplyZoneWiseAccumulationService.update(this.supplyZoneWiseAccumulation));
        }
    }

    protected subscribeToBulkSaveResponse(result: Observable<HttpResponse<ISupplyZoneWiseAccumulation[]>>) {
        result.subscribe(
            (res: HttpResponse<ISupplyZoneWiseAccumulation[]>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    filterZoneManager() {
        this.supplyZoneManagerService
            .query({
                'supplyZoneId.equals': this.supplyZoneWiseAccumulation.supplyZoneId,
                'status.equals': SupplyZoneManagerStatus.ACTIVE
            })
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyZoneManager[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyZoneManager[]>) => response.body)
            )
            .subscribe(
                (res: ISupplyZoneManager[]) => {
                    this.supplyzonemanagers = res;
                    this.filterSupplyAreWiseAccumulation();
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    filterSupplyAreWiseAccumulation() {
        if (
            this.supplyZoneWiseAccumulation.supplyZoneId &&
            this.supplyZoneWiseAccumulation.supplyZoneManagerId &&
            this.supplyZoneWiseAccumulation.zoneWiseAccumulationRefNo
        ) {
            this.supplyAreaWiseAccumulationExtendedService
                .query({
                    'supplyZoneId.equals': this.supplyZoneWiseAccumulation.supplyZoneId,
                    'supplyZoneManagerId.equals': this.supplyZoneWiseAccumulation.supplyZoneManagerId,
                    'status.equals': SupplyAreaWiseAccumulationStatus.FORWARDED
                })
                .subscribe(
                    (res: HttpResponse<ISupplyAreaWiseAccumulation[]>) => {
                        this.paginateSupplyAreaWiseAccumulation(res.body, res.headers);
                        this.selectedAreaWiseAccumulation = [];
                        this.accumulatedList = [];
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        }
    }

    protected paginateSupplyAreaWiseAccumulation(data: ISupplyAreaWiseAccumulation[], headers: HttpHeaders) {
        this.supplyAreaWiseAccumulations = [];
        for (let i = 0; i < data.length; i++) {
            this.supplyAreaWiseAccumulations.push(data[i]);
        }
    }

    assignZoneWiseAccumulationReferenceNo(data: ISupplyZoneWiseAccumulation[], headers: HttpHeaders) {
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
        this.supplyZoneWiseAccumulation.zoneWiseAccumulationRefNo =
            'ZAR-' +
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

    trackId(index: number, item: ISupplyAreaWiseAccumulation) {
        return item.id;
    }

    selectItem(supplyAreaWiseAccumulation: SupplyAreaWiseAccumulation) {
        let selectedItem: SupplyAreaWiseAccumulation;
        let flag: boolean = false;
        for (let a = 0; a < this.selectedAreaWiseAccumulation.length; a++) {
            if (supplyAreaWiseAccumulation.id === this.selectedAreaWiseAccumulation[a].id) {
                this.selectedAreaWiseAccumulation.splice(a, 1);
                flag = true;
                break;
            }
        }
        if (!flag) {
            supplyAreaWiseAccumulation.zoneWiseAccumulationRefNo = this.supplyZoneWiseAccumulation.zoneWiseAccumulationRefNo;
            supplyAreaWiseAccumulation.status = SupplyAreaWiseAccumulationStatus.PENDING;
            this.selectedAreaWiseAccumulation.push(supplyAreaWiseAccumulation);
        }

        this.accumulatedList = [];

        for (let i = 0; i < this.selectedAreaWiseAccumulation.length; i++) {
            let temp: boolean = false;
            for (let j = 0; j < this.accumulatedList.length; j++) {
                temp = true;
                if (
                    this.accumulatedList[j].productCategoryId === this.selectedAreaWiseAccumulation[i].productCategoryId &&
                    this.accumulatedList[j].productId === this.selectedAreaWiseAccumulation[i].productId
                ) {
                    this.accumulatedList[j].quantity = this.accumulatedList[j].quantity + this.selectedAreaWiseAccumulation[i].quantity;
                    this.accumulatedList[j].price = this.accumulatedList[j].price + this.selectedAreaWiseAccumulation[i].price;
                } else {
                    temp = false;
                }
            }

            if (!temp) {
                let scmAreaWiseSelectedProduct: ScmAreaWiseSelectedProduct = new ScmAreaWiseSelectedProduct(null, '', null, '', null, null);
                scmAreaWiseSelectedProduct.productCategoryId = this.selectedAreaWiseAccumulation[i].productCategoryId;
                scmAreaWiseSelectedProduct.productCategoryName = this.selectedAreaWiseAccumulation[i].productCategoryName;
                scmAreaWiseSelectedProduct.productId = this.selectedAreaWiseAccumulation[i].productId;
                scmAreaWiseSelectedProduct.productName = this.selectedAreaWiseAccumulation[i].productName;
                scmAreaWiseSelectedProduct.quantity = this.selectedAreaWiseAccumulation[i].quantity;
                scmAreaWiseSelectedProduct.price = this.selectedAreaWiseAccumulation[i].price;
                this.accumulatedList.push(scmAreaWiseSelectedProduct);
            }
        }
    }
}
