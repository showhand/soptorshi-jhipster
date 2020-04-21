import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { filter, map, switchMap } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPurchaseOrder, PurchaseOrderStatus } from 'app/shared/model/purchase-order.model';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { IQuotation, SelectionType } from 'app/shared/model/quotation.model';
import { QuotationService } from 'app/entities/quotation';
import { EmployeeService } from 'app/entities/employee';
import { AccountService } from 'app/core';
import { IEmployee } from 'app/shared/model/employee.model';
import { PurchaseOrderService, PurchaseOrderUpdateComponent } from 'app/entities/purchase-order';
import { NgForm } from '@angular/forms';
import { PurchaseOrderExtendedService } from 'app/entities/purchase-order-extended/purchase-order-extended.service';
import { IQuotationDetails } from 'app/shared/model/quotation-details.model';
import { QuotationDetailsService } from 'app/entities/quotation-details';
import { of } from 'rxjs';

@Component({
    selector: 'jhi-purchase-order-update',
    templateUrl: './purchase-order-extended-update.component.html'
})
export class PurchaseOrderExtendedUpdateComponent extends PurchaseOrderUpdateComponent implements OnInit {
    currentAccount: any;
    currentEmployee: IEmployee;
    form: any;
    requisition: IRequisition;
    quotation: IQuotation;
    quotationDetails: IQuotationDetails[];

    @ViewChild('editForm') editForm: NgForm;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected purchaseOrderService: PurchaseOrderExtendedService,
        protected requisitionService: RequisitionService,
        protected quotationService: QuotationService,
        protected activatedRoute: ActivatedRoute,
        protected employeeService: EmployeeService,
        public accountService: AccountService,
        private quotationDetailsService: QuotationDetailsService
    ) {
        super(dataUtils, jhiAlertService, purchaseOrderService, requisitionService, quotationService, activatedRoute);
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            console.log('Current account --->');
            console.log(this.currentAccount);
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

            this.activatedRoute.data.subscribe(({ purchaseOrder }) => {
                of(purchaseOrder)
                    .pipe(
                        switchMap(res => {
                            this.purchaseOrder = purchaseOrder;
                            return of(this.purchaseOrder);
                        })
                    )
                    .pipe(
                        filter(res => {
                            if (res.requisitionId) return true;
                            else return false;
                        })
                    )
                    .subscribe(() => this.fetchRequisitionAndQuotationWithDetails());

                this.disableOrEnableComponents();
                this.generatePurchaseOrderNo();
            });
        });

        this.isSaving = false;

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
    }

    downloadReport() {
        this.purchaseOrderService.downloadPurchaseOrderReport(this.purchaseOrder.id);
    }

    fetchRequisitionAndQuotationWithDetails(): void {
        this.requisitionService
            .find(this.purchaseOrder.requisitionId)
            .pipe(map(res => (this.requisition = res.body)))
            .pipe(
                switchMap(() =>
                    this.quotationService.query({
                        'requisitionId.equals': this.requisition.id,
                        'selectionStatus.equals': SelectionType.SELECTED
                    })
                )
            )
            .pipe(
                map(res => {
                    this.purchaseOrder.requisitionId = this.requisition.id;
                    this.quotation = res.body[0];
                    this.purchaseOrder.quotationId = this.quotation.id;
                    return this.quotation;
                })
            )
            .pipe(
                switchMap(res => {
                    return this.quotationDetailsService.query({
                        'quotationId.equals': res.id
                    });
                })
            )
            .subscribe(res => {
                this.quotationDetails = res.body;
            });
    }

    disableOrEnableComponents() {
        if (
            (this.purchaseOrder.status == PurchaseOrderStatus.MODIFICATION_REQUEST_BY_CFO ||
                this.purchaseOrder.status == null ||
                this.purchaseOrder.status == undefined) &&
            this.currentAccount.authorities.includes('ROLE_REQUISITION')
        ) {
            setTimeout(() => {
                this.editForm.form.enable();
            }, 500);
        } else {
            setTimeout(() => {
                this.editForm.form.disable();
            }, 500);
        }
    }

    generatePurchaseOrderNo() {
        console.log('purchase order--->');
        console.log(this.purchaseOrder);
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
                    this.purchaseOrder.purchaseOrderNo =
                        'PO-SOFPL-' + new Date().getFullYear() + '-' + this.zeroPad(res.body.length + 1, 8);
                    this.purchaseOrder.workOrderNo = 'WO-SOFPL-' + new Date().getFullYear() + '-' + this.zeroPad(res.body.length + 1, 8);
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
            this.subscribeToSaveResponse(this.purchaseOrderService.create(this.purchaseOrder));
        }
    }

    saveAndSendForCFOApproval() {
        this.purchaseOrder.status = PurchaseOrderStatus.WAITING_FOR_CFO_APPROVAL;
        this.save();
    }

    saveAndSendForAccountsApproval() {
        this.purchaseOrder.status = PurchaseOrderStatus.WAITING_FOR_ACCOUNTS_APPROVAL;
        this.save();
    }

    modificationRequestByAccounts() {
        this.purchaseOrder.status = PurchaseOrderStatus.MODIFICATION_REQUEST_BY_ACCOUNTS;
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
