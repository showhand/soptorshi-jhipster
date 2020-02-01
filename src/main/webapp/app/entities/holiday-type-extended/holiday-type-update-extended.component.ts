import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HolidayTypeExtendedService } from './holiday-type-extended.service';
import { HolidayTypeUpdateComponent } from 'app/entities/holiday-type';

@Component({
    selector: 'jhi-holiday-type-update-extended',
    templateUrl: './holiday-type-update-extended.component.html'
})
export class HolidayTypeUpdateExtendedComponent extends HolidayTypeUpdateComponent {
    constructor(protected holidayTypeService: HolidayTypeExtendedService, protected activatedRoute: ActivatedRoute) {
        super(holidayTypeService, activatedRoute);
    }
}
