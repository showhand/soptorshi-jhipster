import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from './requisition.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { AccountService } from 'app/core';

@Component({
    selector: 'jhi-requisition-update',
    templateUrl: './requisition-update.component.html'
})
export class RequisitionUpdateComponent implements OnInit {
    currentAccount: any;
    currentEmployee: any;

    requisition: IRequisition;
    isSaving: boolean;

    offices: IOffice[];

    departments: IDepartment[];
    requisitionDateDp: any;
    modifiedOnDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected requisitionService: RequisitionService,
        protected employeeService: EmployeeService,
        protected officeService: OfficeService,
        protected departmentService: DepartmentService,
        protected activatedRoute: ActivatedRoute,
        public accountService: AccountService
    ) {}

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
        this.activatedRoute.data.subscribe(({ requisition }) => {
            this.requisition = requisition;
            this.generateRequisitionNo();
        });
        if (!this.requisition.employeeId) {
            this.employeeService
                .query({
                    'employeeId.equals': this.currentAccount.login.toString()
                })
                .subscribe((res: HttpResponse<IEmployee[]>) => {
                    this.requisition.employeeId = res.body[0].id;
                    this.requisition.departmentId = res.body[0].departmentId;
                    this.requisition.officeId = res.body[0].officeId;
                });
        }
        this.officeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IOffice[]>) => mayBeOk.ok),
                map((response: HttpResponse<IOffice[]>) => response.body)
            )
            .subscribe((res: IOffice[]) => (this.offices = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.departmentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepartment[]>) => response.body)
            )
            .subscribe((res: IDepartment[]) => (this.departments = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    zeroPad(num, places): string {
        var zero = places - num.toString().length + 1;
        return Array(+(zero > 0 && zero)).join('0') + num;
    }

    save() {
        this.isSaving = true;
        if (this.requisition.id !== undefined) {
            this.subscribeToSaveResponse(this.requisitionService.update(this.requisition));
        } else {
            // this.requisition.status = RE;
            this.subscribeToSaveResponse(this.requisitionService.create(this.requisition));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequisition>>) {
        result.subscribe((res: HttpResponse<IRequisition>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOfficeById(index: number, item: IOffice) {
        return item.id;
    }

    trackDepartmentById(index: number, item: IDepartment) {
        return item.id;
    }
}
