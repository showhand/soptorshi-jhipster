import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReferenceInformation } from 'app/shared/model/reference-information.model';

@Component({
    selector: 'jhi-reference-information-detail',
    templateUrl: './reference-information-detail.component.html'
})
export class ReferenceInformationDetailComponent implements OnInit {
    referenceInformation: IReferenceInformation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ referenceInformation }) => {
            this.referenceInformation = referenceInformation;
        });
    }

    previousState() {
        window.history.back();
    }
}
