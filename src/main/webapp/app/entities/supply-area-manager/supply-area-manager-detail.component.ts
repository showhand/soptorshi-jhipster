import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';

@Component({
    selector: 'jhi-supply-area-manager-detail',
    templateUrl: './supply-area-manager-detail.component.html'
})
export class SupplyAreaManagerDetailComponent implements OnInit {
    supplyAreaManager: ISupplyAreaManager;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyAreaManager }) => {
            this.supplyAreaManager = supplyAreaManager;
        });
    }

    previousState() {
        window.history.back();
    }
}
