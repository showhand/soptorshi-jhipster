import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { debounceTime, distinctUntilChanged, filter, map } from 'rxjs/operators';
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
import { RequisitionUpdateComponent } from 'app/entities/requisition';
import { NgbModal, NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { RequisitionDetailsService } from 'app/entities/requisition-details';
import { QuotationService } from 'app/entities/quotation';
import { merge, Observable, Subject } from 'rxjs';
import { NgForm } from '@angular/forms';
import { RequisitionExtendedService } from 'app/entities/requisition-extended/requisition-extended.service';
import { CommercialBudgetService } from 'app/entities/commercial-budget';
import { CommercialProductInfoService } from 'app/entities/commercial-product-info';
import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';
import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';

@Component({
    selector: 'jhi-requisition-extended-update',
    templateUrl: './requisition-extended-update.component.html'
})
export class RequisitionExtendedUpdateComponent extends RequisitionUpdateComponent implements OnInit {
    currentAccount: any;
    currentEmployee: IEmployee;

    employees: IEmployee[];

    offices: IOffice[];

    commercialBudget: ICommercialBudget;
    commercialProductInfos: ICommercialProductInfo[];

    productcategories: IProductCategory[];
    productCategory: IProductCategory;
    selectedProductCategory: string;
    productCategoryNameMapId: any;
    productCategoryNameList: string[];
    productCategoryIdMapName: any;

    @ViewChild('instance') instance: NgbTypeahead;
    @ViewChild('editForm') editForm: NgForm;

    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected requisitionService: RequisitionExtendedService,
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
        protected eventManager: JhiEventManager,
        protected router: Router,
        private commercialBudgetService: CommercialBudgetService,
        private commercialProductInfoService: CommercialProductInfoService
    ) {
        super(
            dataUtils,
            jhiAlertService,
            requisitionService,
            employeeService,
            officeService,
            productCategoryService,
            departmentService,
            activatedRoute
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
            this.selectedProductCategory = this.requisition.productCategoryName;
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
            .subscribe(
                (res: IProductCategory[]) => {
                    this.productcategories = res;
                    this.productCategoryNameMapId = {};
                    this.productCategoryNameList = [];
                    this.productCategoryIdMapName = {};
                    this.productcategories.forEach((p: IProductCategory) => {
                        this.productCategoryNameMapId[p.name] = p.id;
                        this.productCategoryIdMapName[p.id] = p.name;
                        this.productCategoryNameList.push(p.name);
                    });
                },

                (res: HttpErrorResponse) => this.onError(res.message)
            );

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

    disableOrEnableComponents() {
        if (
            (this.requisition.status == RequisitionStatus.MODIFICATION_REQUEST_BY_CFO ||
                this.requisition.status == RequisitionStatus.MODIFICATION_REQUEST_BY_PURCHASE_COMMITTEE ||
                this.requisition.status == null ||
                this.requisition.status == undefined) &&
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
        this.requisition.productCategoryId = this.productCategoryNameMapId[this.selectedProductCategory];
        if (this.requisition.id !== undefined) {
            this.subscribeToSaveResponse(this.requisitionService.update(this.requisition));
        } else {
            this.subscribeToSaveResponse(this.requisitionService.create(this.requisition));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequisition>>) {
        result.subscribe(
            (rZes: HttpResponse<IRequisition>) => {
                this.requisition = rZes.body;
                this.isSaving = false;
                this.router.navigate(['/requisition', this.requisition.id, 'edit']);
            },
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    forwardToHead() {
        this.requisition.status = RequisitionStatus.WAITING_FOR_HEADS_APPROVAL;
        this.save();
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
        //this.requisition.refToPurchaseCommittee = this.currentEmployee.id;
        this.save();
    }
    rejectByPurchaseCommittee() {
        this.requisition.status = RequisitionStatus.REJECTED_BY_PURCHASE_COMMITTEE;
        //this.requisition.refToPurchaseCommittee = this.currentEmployee.id;
        this.save();
    }

    approveByCFO() {
        this.requisition.status = RequisitionStatus.APPROVED_BY_CFO;
        //this.requisition.refToPurchaseCommittee = this.currentEmployee.id;
        this.save();
    }
    rejectByCFO() {
        this.requisition.status = RequisitionStatus.REJECTED_BY_CFO;
        //this.requisition.refToPurchaseCommittee = this.currentEmployee.id;
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

    public createPurchaseOrder(): void {
        const requisitionId = this.requisition.id;
        this.router.navigate(['/purchase-order', requisitionId, 'create']);
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

    search = (text$: Observable<string>) => {
        const debouncedText$ = text$.pipe(
            debounceTime(200),
            distinctUntilChanged()
        );
        const clicksWithClosedPopup$ = this.click$.pipe(filter(() => !this.instance.isPopupOpen()));
        const inputFocus$ = this.focus$;

        return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
            map(term =>
                (term === ''
                    ? this.productCategoryNameList
                    : this.productCategoryNameList.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)
                ).slice(0, 10)
            )
        );
    };
}
