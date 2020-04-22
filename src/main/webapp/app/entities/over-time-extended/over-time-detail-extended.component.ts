import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOverTime } from 'app/shared/model/over-time.model';
import { OverTimeDetailComponent } from 'app/entities/over-time';

@Component({
    selector: 'jhi-over-time-detail-extended',
    templateUrl: './over-time-detail-extended.component.html'
})
export class OverTimeDetailExtendedComponent extends OverTimeDetailComponent {
    overTime: IOverTime;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
