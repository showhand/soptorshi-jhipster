import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from './holiday-type.service';

@Component({
    selector: 'jhi-holiday-type-update',
    templateUrl: './holiday-type-update.component.html'
})
export class HolidayTypeUpdateComponent implements OnInit {
    holidayType: IHolidayType;
    isSaving: boolean;
    createdOn: string;
    updatedOn: string;

    constructor(protected holidayTypeService: HolidayTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ holidayType }) => {
            this.holidayType = holidayType;
            this.createdOn = this.holidayType.createdOn != null ? this.holidayType.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.holidayType.updatedOn != null ? this.holidayType.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.holidayType.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.holidayType.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.holidayType.id !== undefined) {
            this.subscribeToSaveResponse(this.holidayTypeService.update(this.holidayType));
        } else {
            this.subscribeToSaveResponse(this.holidayTypeService.create(this.holidayType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHolidayType>>) {
        result.subscribe((res: HttpResponse<IHolidayType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
