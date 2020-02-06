import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { WeekendDetailComponent } from 'app/entities/weekend';

@Component({
    selector: 'jhi-weekend-detail-extended',
    templateUrl: './weekend-detail-extended.component.html'
})
export class WeekendDetailExtendedComponent extends WeekendDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
