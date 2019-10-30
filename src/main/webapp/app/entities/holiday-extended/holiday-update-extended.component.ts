import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayExtendedService } from './holiday-extended.service';
import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from 'app/entities/holiday-type';
import { HolidayUpdateComponent } from 'app/entities/holiday';

@Component({
    selector: 'jhi-holiday-update-extended',
    templateUrl: './holiday-update-extended.component.html'
})
export class HolidayUpdateExtendedComponent extends HolidayUpdateComponent implements OnInit {
    holiday: IHoliday;
    isSaving: boolean;

    holidaytypes: IHolidayType[];
    fromDateDp: any;
    toDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected holidayService: HolidayExtendedService,
        protected holidayTypeService: HolidayTypeService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, holidayService, holidayTypeService, activatedRoute);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ holiday }) => {
            this.holiday = holiday;
        });
        this.holidayTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IHolidayType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IHolidayType[]>) => response.body)
            )
            .subscribe((res: IHolidayType[]) => (this.holidaytypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
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

    calculateDifference() {
        this.holiday.numberOfDays =
            this.holiday.toDate && this.holiday.fromDate ? this.holiday.toDate.diff(this.holiday.fromDate, 'days') + 1 : 0;
    }
}
