import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISalaryMessages } from 'app/shared/model/salary-messages.model';
import { SalaryMessagesService } from './salary-messages.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from 'app/entities/monthly-salary';

@Component({
    selector: 'jhi-salary-messages-update',
    templateUrl: './salary-messages-update.component.html'
})
export class SalaryMessagesUpdateComponent implements OnInit {
    salaryMessages: ISalaryMessages;
    isSaving: boolean;

    employees: IEmployee[];

    monthlysalaries: IMonthlySalary[];
    commentedOnDp: any;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected salaryMessagesService: SalaryMessagesService,
        protected employeeService: EmployeeService,
        protected monthlySalaryService: MonthlySalaryService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ salaryMessages }) => {
            this.salaryMessages = salaryMessages;
        });
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.monthlySalaryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMonthlySalary[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMonthlySalary[]>) => response.body)
            )
            .subscribe((res: IMonthlySalary[]) => (this.monthlysalaries = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.salaryMessages.id !== undefined) {
            this.subscribeToSaveResponse(this.salaryMessagesService.update(this.salaryMessages));
        } else {
            this.subscribeToSaveResponse(this.salaryMessagesService.create(this.salaryMessages));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalaryMessages>>) {
        result.subscribe((res: HttpResponse<ISalaryMessages>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMonthlySalaryById(index: number, item: IMonthlySalary) {
        return item.id;
    }
}
