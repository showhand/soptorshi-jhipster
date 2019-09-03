import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITermsAndConditions } from 'app/shared/model/terms-and-conditions.model';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { TermsAndConditionsService, TermsAndConditionsUpdateComponent } from 'app/entities/terms-and-conditions';

@Component({
    selector: 'jhi-terms-and-conditions-extended-update',
    templateUrl: './terms-and-conditions-extended-update.component.html'
})
export class TermsAndConditionsExtendedUpdateComponent extends TermsAndConditionsUpdateComponent implements OnInit {
    purchaseorders: IPurchaseOrder[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected termsAndConditionsService: TermsAndConditionsService,
        protected purchaseOrderService: PurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(dataUtils, jhiAlertService, termsAndConditionsService, purchaseOrderService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ termsAndConditions }) => {
            this.termsAndConditions = termsAndConditions;
        });
        this.purchaseOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPurchaseOrder[]>) => response.body)
            )
            .subscribe((res: IPurchaseOrder[]) => (this.purchaseorders = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    trackPurchaseOrderById(index: number, item: IPurchaseOrder) {
        return item.id;
    }
}
