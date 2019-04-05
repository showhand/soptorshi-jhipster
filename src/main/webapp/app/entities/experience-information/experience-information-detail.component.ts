import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExperienceInformation } from 'app/shared/model/experience-information.model';

@Component({
    selector: 'jhi-experience-information-detail',
    templateUrl: './experience-information-detail.component.html'
})
export class ExperienceInformationDetailComponent implements OnInit {
    experienceInformation: IExperienceInformation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ experienceInformation }) => {
            this.experienceInformation = experienceInformation;
        });
    }

    previousState() {
        window.history.back();
    }
}
