import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPi } from 'app/shared/model/commercial-pi.model';

@Component({
    selector: 'jhi-commercial-pi-detail',
    templateUrl: './commercial-pi-detail.component.html'
})
export class CommercialPiDetailComponent implements OnInit {
    commercialPi: ICommercialPi;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPi }) => {
            this.commercialPi = commercialPi;
        });
    }

    previousState() {
        window.history.back();
    }
}
