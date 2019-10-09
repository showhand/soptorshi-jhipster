import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPredefinedNarration } from 'app/shared/model/predefined-narration.model';
import { PredefinedNarrationDetailComponent } from 'app/entities/predefined-narration';

@Component({
    selector: 'jhi-predefined-narration-detail',
    templateUrl: './predefined-narration-extended-detail.component.html'
})
export class PredefinedNarrationExtendedDetailComponent extends PredefinedNarrationDetailComponent implements OnInit {
    predefinedNarration: IPredefinedNarration;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
