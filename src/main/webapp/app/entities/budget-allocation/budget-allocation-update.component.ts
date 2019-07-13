import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBudgetAllocation } from 'app/shared/model/budget-allocation.model';
import { BudgetAllocationService } from './budget-allocation.service';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from 'app/entities/office';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';

@Component({
    selector: 'jhi-budget-allocation-update',
    templateUrl: './budget-allocation-update.component.html'
})
export class BudgetAllocationUpdateComponent implements OnInit {
    budgetAllocation: IBudgetAllocation;
    isSaving: boolean;

    offices: IOffice[];

    departments: IDepartment[];

    financialaccountyears: IFinancialAccountYear[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected budgetAllocationService: BudgetAllocationService,
        protected officeService: OfficeService,
        protected departmentService: DepartmentService,
        protected financialAccountYearService: FinancialAccountYearService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ budgetAllocation }) => {
            this.budgetAllocation = budgetAllocation;
        });
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
        this.financialAccountYearService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFinancialAccountYear[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFinancialAccountYear[]>) => response.body)
            )
            .subscribe(
                (res: IFinancialAccountYear[]) => (this.financialaccountyears = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.budgetAllocation.id !== undefined) {
            this.subscribeToSaveResponse(this.budgetAllocationService.update(this.budgetAllocation));
        } else {
            this.subscribeToSaveResponse(this.budgetAllocationService.create(this.budgetAllocation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBudgetAllocation>>) {
        result.subscribe((res: HttpResponse<IBudgetAllocation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOfficeById(index: number, item: IOffice) {
        return item.id;
    }

    trackDepartmentById(index: number, item: IDepartment) {
        return item.id;
    }

    trackFinancialAccountYearById(index: number, item: IFinancialAccountYear) {
        return item.id;
    }
}
