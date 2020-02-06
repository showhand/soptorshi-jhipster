import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HolidayTypeDetailComponent } from 'app/entities/holiday-type';

@Component({
    selector: 'jhi-holiday-type-detail-extended',
    templateUrl: './holiday-type-detail-extended.component.html'
})
export class HolidayTypeDetailExtendedComponent extends HolidayTypeDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
