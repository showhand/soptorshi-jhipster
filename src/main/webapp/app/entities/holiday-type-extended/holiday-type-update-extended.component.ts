import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeExtendedService } from './holiday-type-extended.service';
import { HolidayTypeUpdateComponent } from 'app/entities/holiday-type';

@Component({
    selector: 'jhi-holiday-type-update-extended',
    templateUrl: './holiday-type-update-extended.component.html'
})
export class HolidayTypeUpdateExtendedComponent extends HolidayTypeUpdateComponent implements OnInit {
    holidayType: IHolidayType;
    isSaving: boolean;

    constructor(protected holidayTypeService: HolidayTypeExtendedService, protected activatedRoute: ActivatedRoute) {
        super(holidayTypeService, activatedRoute);
    }

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
