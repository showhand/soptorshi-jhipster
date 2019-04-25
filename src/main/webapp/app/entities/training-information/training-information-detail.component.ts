import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingInformation } from 'app/shared/model/training-information.model';

@Component({
    selector: 'jhi-training-information-detail',
    templateUrl: './training-information-detail.component.html'
})
export class TrainingInformationDetailComponent implements OnInit {
    trainingInformation: ITrainingInformation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ trainingInformation }) => {
            this.trainingInformation = trainingInformation;
        });
    }

    previousState() {
        window.history.back();
    }
}
