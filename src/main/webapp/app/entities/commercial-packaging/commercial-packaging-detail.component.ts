import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';

@Component({
    selector: 'jhi-commercial-packaging-detail',
    templateUrl: './commercial-packaging-detail.component.html'
})
export class CommercialPackagingDetailComponent implements OnInit {
    commercialPackaging: ICommercialPackaging;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPackaging }) => {
            this.commercialPackaging = commercialPackaging;
        });
    }

    previousState() {
        window.history.back();
    }
}
