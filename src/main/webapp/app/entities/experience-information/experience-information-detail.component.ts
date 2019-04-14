import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExperienceInformation } from 'app/shared/model/experience-information.model';

@Component({
    selector: 'jhi-experience-information-detail',
    templateUrl: './experience-information-detail.component.html'
})
export class ExperienceInformationDetailComponent implements OnInit {
    @Input()
    experienceInformation: IExperienceInformation;
    @Output()
    showExperienceInformationSection: EventEmitter<any> = new EventEmitter();
    @Output()
    editExperienceInformation: EventEmitter<any> = new EventEmitter();

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {}

    edit() {
        this.editExperienceInformation.emit();
    }

    previousState() {
        this.showExperienceInformationSection.emit();
    }
}
