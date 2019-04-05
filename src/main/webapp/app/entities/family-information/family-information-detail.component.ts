import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFamilyInformation } from 'app/shared/model/family-information.model';

@Component({
    selector: 'jhi-family-information-detail',
    templateUrl: './family-information-detail.component.html'
})
export class FamilyInformationDetailComponent implements OnInit {
    familyInformation: IFamilyInformation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ familyInformation }) => {
            this.familyInformation = familyInformation;
        });
    }

    previousState() {
        window.history.back();
    }
}
