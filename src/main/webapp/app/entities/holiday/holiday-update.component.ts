import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayService } from './holiday.service';
import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from 'app/entities/holiday-type';

@Component({
    selector: 'jhi-holiday-update',
    templateUrl: './holiday-update.component.html'
})
export class HolidayUpdateComponent implements OnInit {
    holiday: IHoliday;
    isSaving: boolean;

    holidaytypes: IHolidayType[];
    fromDateDp: any;
    toDateDp: any;
    createdOn: string;
    updatedOn: string;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected holidayService: HolidayService,
        protected holidayTypeService: HolidayTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ holiday }) => {
            this.holiday = holiday;
            this.createdOn = this.holiday.createdOn != null ? this.holiday.createdOn.format(DATE_TIME_FORMAT) : null;
            this.updatedOn = this.holiday.updatedOn != null ? this.holiday.updatedOn.format(DATE_TIME_FORMAT) : null;
        });
        this.holidayTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IHolidayType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IHolidayType[]>) => response.body)
            )
            .subscribe((res: IHolidayType[]) => (this.holidaytypes = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.holiday.createdOn = this.createdOn != null ? moment(this.createdOn, DATE_TIME_FORMAT) : null;
        this.holiday.updatedOn = this.updatedOn != null ? moment(this.updatedOn, DATE_TIME_FORMAT) : null;
        if (this.holiday.id !== undefined) {
            this.subscribeToSaveResponse(this.holidayService.update(this.holiday));
        } else {
            this.subscribeToSaveResponse(this.holidayService.create(this.holiday));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IHoliday>>) {
        result.subscribe((res: HttpResponse<IHoliday>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackHolidayTypeById(index: number, item: IHolidayType) {
        return item.id;
    }
}
