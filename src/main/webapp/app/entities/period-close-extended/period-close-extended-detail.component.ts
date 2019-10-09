import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPeriodClose } from 'app/shared/model/period-close.model';
import { PeriodCloseDetailComponent } from 'app/entities/period-close';

@Component({
    selector: 'jhi-period-close-extended-detail',
    templateUrl: './period-close-extended-detail.component.html'
})
export class PeriodCloseExtendedDetailComponent extends PeriodCloseDetailComponent implements OnInit {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
