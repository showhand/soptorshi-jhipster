import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainingInformation } from 'app/shared/model/training-information.model';

@Component({
    selector: 'jhi-training-information-detail',
    templateUrl: './training-information-detail.component.html'
})
export class TrainingInformationDetailComponent implements OnInit {
    @Input()
    trainingInformation: ITrainingInformation;
    @Output()
    showTrainingInformationSection: EventEmitter<any> = new EventEmitter();
    @Output()
    editTrainingInformation: EventEmitter<any> = new EventEmitter();

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {}

    edit() {
        this.editTrainingInformation.emit();
    }

    previousState() {
        this.showTrainingInformationSection.emit();
    }
}
