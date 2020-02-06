import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HolidayDetailComponent } from 'app/entities/holiday';
import { JhiDataUtils } from 'ng-jhipster';

@Component({
    selector: 'jhi-holiday-detail-extended',
    templateUrl: './holiday-detail-extended.component.html'
})
export class HolidayDetailExtendedComponent extends HolidayDetailComponent {
    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}
