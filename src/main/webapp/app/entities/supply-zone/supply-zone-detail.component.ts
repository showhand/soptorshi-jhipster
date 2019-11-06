import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyZone } from 'app/shared/model/supply-zone.model';

@Component({
    selector: 'jhi-supply-zone-detail',
    templateUrl: './supply-zone-detail.component.html'
})
export class SupplyZoneDetailComponent implements OnInit {
    supplyZone: ISupplyZone;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyZone }) => {
            this.supplyZone = supplyZone;
        });
    }

    previousState() {
        window.history.back();
    }
}
