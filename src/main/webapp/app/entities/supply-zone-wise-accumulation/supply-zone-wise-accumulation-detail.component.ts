import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';

@Component({
    selector: 'jhi-supply-zone-wise-accumulation-detail',
    templateUrl: './supply-zone-wise-accumulation-detail.component.html'
})
export class SupplyZoneWiseAccumulationDetailComponent implements OnInit {
    supplyZoneWiseAccumulation: ISupplyZoneWiseAccumulation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyZoneWiseAccumulation }) => {
            this.supplyZoneWiseAccumulation = supplyZoneWiseAccumulation;
        });
    }

    previousState() {
        window.history.back();
    }
}
