import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcademicInformation } from 'app/shared/model/academic-information.model';

@Component({
    selector: 'jhi-academic-information-detail',
    templateUrl: './academic-information-detail.component.html'
})
export class AcademicInformationDetailComponent implements OnInit {
    academicInformation: IAcademicInformation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ academicInformation }) => {
            this.academicInformation = academicInformation;
        });
    }

    previousState() {
        window.history.back();
    }
}
