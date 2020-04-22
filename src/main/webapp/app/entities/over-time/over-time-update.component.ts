import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOverTime } from 'app/shared/model/over-time.model';
import { OverTimeService } from './over-time.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-over-time-update',
    templateUrl: './over-time-update.component.html'
})
export class OverTimeUpdateComponent implements OnInit {
    overTime: IOverTime;
    isSaving: boolean;

    employees: IEmployee[];
    overTimeDateDp: any;
    fromTime: string;
    toTime: string;
    createdOn: string;
    updatedOn: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected overTimeService: OverTimeService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ overTime }) => {
            this.overTime = overTime;
            this.fromTime = this.overTime.fromTime != null ? this.overTime.fromTime.format(DATE_TIME_FORMAT) : null;
            this.toTime = this.overTime.toTime != null ? this.overTime.toTime.format(DATE_TIME_FORMAT) : null;
            this.createdOn = this.overTime.createdOn != null ? this.overTime.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.overTime.updatedOn != null ? this.overTime.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.overTime.fromTime = this.fromTime != null ? moment(this.fromTime, DATE_TIME_FORMAT) : null;
        this.overTime.toTime = this.toTime != null ? moment(this.toTime, DATE_TIME_FORMAT) : null;
        this.overTime.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.overTime.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.overTime.id !== undefined) {
            this.subscribeToSaveResponse(this.overTimeService.update(this.overTime));
        } else {
            this.subscribeToSaveResponse(this.overTimeService.create(this.overTime));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOverTime>>) {
        result.subscribe((res: HttpResponse<IOverTime>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
