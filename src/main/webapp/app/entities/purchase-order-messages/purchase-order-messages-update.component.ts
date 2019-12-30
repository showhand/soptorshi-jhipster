import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';
import { PurchaseOrderMessagesService } from './purchase-order-messages.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order';

@Component({
    selector: 'jhi-purchase-order-messages-update',
    templateUrl: './purchase-order-messages-update.component.html'
})
export class PurchaseOrderMessagesUpdateComponent implements OnInit {
    purchaseOrderMessages: IPurchaseOrderMessages;
    isSaving: boolean;

    employees: IEmployee[];

    purchaseorders: IPurchaseOrder[];
    commentedOnDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected purchaseOrderMessagesService: PurchaseOrderMessagesService,
        protected employeeService: EmployeeService,
        protected purchaseOrderService: PurchaseOrderService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseOrderMessages }) => {
            this.purchaseOrderMessages = purchaseOrderMessages;
        });
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.purchaseOrderService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IPurchaseOrder[]>) => mayBeOk.ok),
                map((response: HttpResponse<IPurchaseOrder[]>) => response.body)
            )
            .subscribe((res: IPurchaseOrder[]) => (this.purchaseorders = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.purchaseOrderMessages.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseOrderMessagesService.update(this.purchaseOrderMessages));
        } else {
            this.subscribeToSaveResponse(this.purchaseOrderMessagesService.create(this.purchaseOrderMessages));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrderMessages>>) {
        result.subscribe(
            (res: HttpResponse<IPurchaseOrderMessages>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }

    trackPurchaseOrderById(index: number, item: IPurchaseOrder) {
        return item.id;
    }
}
