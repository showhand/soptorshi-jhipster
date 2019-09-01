import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPurchaseOrder, PurchaseOrderStatus } from 'app/shared/model/purchase-order.model';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationService } from 'app/entities/quotation';
import { EmployeeService } from 'app/entities/employee';
import { AccountService } from 'app/core';
import { IEmployee } from 'app/shared/model/employee.model';
import { PurchaseOrderService, PurchaseOrderUpdateComponent } from 'app/entities/purchase-order';

@Component({
    selector: 'jhi-purchase-order-update',
    templateUrl: './purchase-order-extended-update.component.html'
})
export class PurchaseOrderExtendedUpdateComponent extends PurchaseOrderUpdateComponent implements OnInit {
    currentAccount: any;
    currentEmployee: IEmployee;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected purchaseOrderService: PurchaseOrderService,
        protected requisitionService: RequisitionService,
        protected quotationService: QuotationService,
        protected activatedRoute: ActivatedRoute,
        protected employeeService: EmployeeService,
        public accountService: AccountService
    ) {
        super(
            dataUtils,
            jhiAlertService,
            purchaseOrderService,
            requisitionService,
            quotationService,
            activatedRoute,
            employeeService,
            accountService
        );
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.employeeService
                .query({
                    'employeeId.equals': this.currentAccount.login
                })
                .subscribe(
                    (res: HttpResponse<IEmployee[]>) => {
                        this.currentEmployee = res.body[0];
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });

        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseOrder }) => {
            this.purchaseOrder = purchaseOrder;
        });
        this.requisitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRequisition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRequisition[]>) => response.body)
            )
            .subscribe((res: IRequisition[]) => (this.requisitions = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.quotationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IQuotation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IQuotation[]>) => response.body)
            )
            .subscribe((res: IQuotation[]) => (this.quotations = res), (res: HttpErrorResponse) => this.onError(res.message));

        this.generatePurchaseOrderNo();
    }

    generatePurchaseOrderNo() {
        if (!this.purchaseOrder.purchaseOrderNo) {
            const dateStrFrom = new Date().getFullYear() + '-01-01';
            const dateStrTo = new Date().getFullYear() + '-12-31';
            this.purchaseOrderService
                .query({
                    'issueDate.greaterOrEqualThan': dateStrFrom,
                    'issueDate.lessOrEqualThan': dateStrTo
                })
                .subscribe((res: HttpResponse<IPurchaseOrder[]>) => {
                    const dateStr = moment(new Date()).format('DD-MM-YYYY');
                    this.purchaseOrder.purchaseOrderNo = 'PO-SOFPL-' + this.zeroPad(res.body.length + 1, 8);
                    this.purchaseOrder.workOrderNo = 'WO-SOFPL-' + this.zeroPad(res.body.length + 1, 8);
                });
        }
    }

    zeroPad(num, places): string {
        const zero = places - num.toString().length + 1;
        return Array(+(zero > 0 && zero)).join('0') + num;
    }

    save() {
        this.isSaving = true;
        if (this.purchaseOrder.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseOrderService.update(this.purchaseOrder));
        } else {
            this.purchaseOrder.status = PurchaseOrderStatus.WAITING_FOR_CFO_APPROVAL;
            this.subscribeToSaveResponse(this.purchaseOrderService.create(this.purchaseOrder));
        }
    }

    saveAndSendForCFOApproval() {
        this.purchaseOrder.status = PurchaseOrderStatus.WAITING_FOR_CFO_APPROVAL;
        this.save();
    }

    approveByCfo() {
        this.purchaseOrder.status = PurchaseOrderStatus.APPROVED_BY_CFO;
        this.save();
    }

    rejectedByCfo() {
        this.purchaseOrder.status = PurchaseOrderStatus.REJECTED_BY_CFO;
        this.save();
    }

    requestForModificationByCfo() {
        this.purchaseOrder.status = PurchaseOrderStatus.MODIFICATION_REQUEST_BY_CFO;
        this.save();
    }
}
