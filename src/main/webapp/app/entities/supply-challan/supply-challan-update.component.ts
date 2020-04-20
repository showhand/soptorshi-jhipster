import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISupplyChallan } from 'app/shared/model/supply-challan.model';
import { SupplyChallanService } from './supply-challan.service';
import { ISupplyOrder } from 'app/shared/model/supply-order.model';
import { SupplyOrderService } from 'app/entities/supply-order';

@Component({
    selector: 'jhi-supply-challan-update',
    templateUrl: './supply-challan-update.component.html'
})
export class SupplyChallanUpdateComponent implements OnInit {
    supplyChallan: ISupplyChallan;
    isSaving: boolean;

    supplyorders: ISupplyOrder[];
    dateOfChallanDp: any;
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected supplyChallanService: SupplyChallanService,
        protected supplyOrderService: SupplyOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ supplyChallan }) => {
            this.supplyChallan = supplyChallan;
            this.createdOn = this.supplyChallan.createdOn != null ? this.supplyChallan.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.supplyChallan.updatedOn != null ? this.supplyChallan.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.supplyOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISupplyOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISupplyOrder[]>) => response.body)
            )
            .subscribe((res: ISupplyOrder[]) => (this.supplyorders = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.supplyChallan.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.supplyChallan.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.supplyChallan.id !== undefined) {
            this.subscribeToSaveResponse(this.supplyChallanService.update(this.supplyChallan));
        } else {
            this.subscribeToSaveResponse(this.supplyChallanService.create(this.supplyChallan));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplyChallan>>) {
        result.subscribe((res: HttpResponse<ISupplyChallan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSupplyOrderById(index: number, item: ISupplyOrder) {
        return item.id;
    }
}
