import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPredefinedNarration } from 'app/shared/model/predefined-narration.model';

@Component({
    selector: 'jhi-predefined-narration-detail',
    templateUrl: './predefined-narration-detail.component.html'
})
export class PredefinedNarrationDetailComponent implements OnInit {
    predefinedNarration: IPredefinedNarration;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ predefinedNarration }) => {
            this.predefinedNarration = predefinedNarration;
        });
    }

    previousState() {
        window.history.back();
    }
}
