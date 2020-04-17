import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';

@Component({
    selector: 'jhi-supply-zone-manager-detail',
    templateUrl: './supply-zone-manager-detail.component.html'
})
export class SupplyZoneManagerDetailComponent implements OnInit {
    supplyZoneManager: ISupplyZoneManager;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyZoneManager }) => {
            this.supplyZoneManager = supplyZoneManager;
        });
    }

    previousState() {
        window.history.back();
    }
}
