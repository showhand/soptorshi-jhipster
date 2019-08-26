import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { IRequisition, RequisitionStatus } from 'app/shared/model/requisition.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { AccountService } from 'app/core';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { IPurchaseOrder, PurchaseOrderStatus } from 'app/shared/model/purchase-order.model';
import { RequisitionService, RequisitionUpdateComponent } from 'app/entities/requisition';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RequisitionDetailsService } from 'app/entities/requisition-details';
import { QuotationDetailsService } from 'app/entities/quotation-details';
import { QuotationService } from 'app/entities/quotation';
import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { IQuotation } from 'app/shared/model/quotation.model';

@Component({
    selector: 'jhi-requisition-extended-update',
    templateUrl: './requisition-extended-update.component.html'
})
export class RequisitionExtendedUpdateComponent extends RequisitionUpdateComponent implements OnInit {
    currentAccount: any;
    currentEmployee: IEmployee;

    employees: IEmployee[];

    offices: IOffice[];

    productcategories: IProductCategory[];
    productCategory: IProductCategory;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected requisitionService: RequisitionService,
        protected employeeService: EmployeeService,
        protected officeService: OfficeService,
        protected productCategoryService: ProductCategoryService,
        protected departmentService: DepartmentService,
        protected activatedRoute: ActivatedRoute,
        public accountService: AccountService,
        protected purchaseOrderService: PurchaseOrderService,
        public modalService: NgbModal,
        protected requisitionDetailsService: RequisitionDetailsService,
        protected quotationService: QuotationService,
        protected eventManager: JhiEventManager
    ) {
        super(
            dataUtils,
            jhiAlertService,
            requisitionService,
            employeeService,
            officeService,
            productCategoryService,
            departmentService,
            activatedRoute,
            accountService,
            purchaseOrderService
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
                        if (!this.requisition.employeeId) {
                            this.requisition.employeeId = res.body[0].id;
                        }
                        if (!this.requisition.departmentId) {
                            this.requisition.departmentId = res.body[0].departmentId;
                        }
                        if (!this.requisition.officeId) {
                            this.requisition.officeId = res.body[0].officeId;
                        }
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
        });

        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ requisition }) => {
            this.requisition = requisition;
            this.generateRequisitionNo();
        });

        this.officeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOffice[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOffice[]>) => response.body)
            )
            .subscribe((res: IOffice[]) => (this.offices = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productCategoryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductCategory[]>) => response.body)
            )
            .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));

        if (this.requisition.productCategoryId) {
            this.productCategoryService.find(this.requisition.productCategoryId).subscribe((res: HttpResponse<IProductCategory>) => {
                this.productCategory = res.body;
            });
        }
        this.departmentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepartment[]>) => response.body)
            )
            .subscribe((res: IDepartment[]) => (this.departments = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    filterProductCategories(event) {
        this.productCategoryService
            .query({
                'name.contains': event.query.toString()
            })
            .subscribe((res: HttpResponse<IProductCategory[]>) => {
                this.productcategories = [];
                this.productcategories = res.body;
            });
    }

    generateRequisitionNo() {
        if (!this.requisition.requisitionNo) {
            const dateStrFrom = new Date().getFullYear() + '-01-01';
            const dateFrom: Date = moment(dateStrFrom, 'DD-MM-YYYY').toDate();
            const dateStrTo = new Date().getFullYear() + '-12-31';
            const dateTo: Date = moment(dateStrTo, 'DD-MM-YYYY').toDate();
            this.requisitionService
                .query({
                    'requisitionDate.greaterOrEqualThan': dateStrFrom,
                    'requisitionDate.lessOrEqualThan': dateStrTo
                })
                .subscribe((res: HttpResponse<IRequisition[]>) => {
                    const dateStr = moment(new Date()).format('DD-MM-YYYY');
                    this.requisition.requisitionNo = 'SOFPL-PR-' + dateStr + '-' + this.zeroPad(res.body.length + 1, 5);
                });
        }
    }

    zeroPad(num, places): string {
        const zero = places - num.toString().length + 1;
        return Array(+(zero > 0 && zero)).join('0') + num;
    }

    save() {
        this.isSaving = true;
        if (this.requisition.id !== undefined) {
            this.subscribeToSaveResponse(this.requisitionService.update(this.requisition));
        } else {
            this.requisition.status = RequisitionStatus.WAITING_FOR_HEADS_APPROVAL;
            this.subscribeToSaveResponse(this.requisitionService.create(this.requisition));
        }
    }

    approveByHead() {
        this.requisition.status = RequisitionStatus.FORWARDED_BY_HEAD;
        this.save();
    }
    rejectByHead() {
        this.requisition.status = RequisitionStatus.REJECTED_BY_HEAD;
        this.save();
    }
    approveByPurchaseCommittee() {
        this.requisition.status = RequisitionStatus.FORWARDED_BY_PURCHASE_COMMITTEE;
        this.requisition.refToPurchaseCommittee = this.currentEmployee.id;
        this.save();
    }
    rejectByPurchaseCommittee() {
        this.requisition.status = RequisitionStatus.REJECTED_BY_PURCHASE_COMMITTEE;
        this.requisition.refToPurchaseCommittee = this.currentEmployee.id;
        this.save();
    }

    approveByCFO() {
        this.requisition.status = RequisitionStatus.APPROVED_BY_CFO;
        this.requisition.refToPurchaseCommittee = this.currentEmployee.id;
        this.save();
    }
    rejectByCFO() {
        this.requisition.status = RequisitionStatus.REJECTED_BY_CFO;
        this.requisition.refToPurchaseCommittee = this.currentEmployee.id;
        this.save();
    }

    receivedByRequester() {
        this.requisition.status = RequisitionStatus.RECEIVED_BY_REQUISIONER;
        this.save();
    }

    receivedAndVerifiedByHead() {
        this.requisition.status = RequisitionStatus.RECEIVED_VERIFIED_BY_HEAD;
        this.save();
    }

    closedByCFO() {
        this.purchaseOrderService
            .query({
                'requisitionId.equals': this.requisition.id
            })
            .subscribe((res: HttpResponse<IPurchaseOrder[]>) => {
                const purchaseOrder = res.body[0];
                purchaseOrder.status = PurchaseOrderStatus.CLOSED_BY_CFO;
                this.purchaseOrderService.update(purchaseOrder);

                this.requisition.status = RequisitionStatus.CLOSED_BY_CFO;
                this.save();
            });
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }

    trackOfficeById(index: number, item: IOffice) {
        return item.id;
    }

    trackProductCategoryById(index: number, item: IProductCategory) {
        return item.id;
    }

    trackDepartmentById(index: number, item: IDepartment) {
        return item.id;
    }
}
