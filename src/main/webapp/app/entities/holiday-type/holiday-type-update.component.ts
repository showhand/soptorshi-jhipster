import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from './holiday-type.service';

@Component({
    selector: 'jhi-holiday-type-update',
    templateUrl: './holiday-type-update.component.html'
})
export class HolidayTypeUpdateComponent implements OnInit {
    holidayType: IHolidayType;
    isSaving: boolean;

    constructor(protected holidayTypeService: HolidayTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ holidayType }) => {
            this.holidayType = holidayType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
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
