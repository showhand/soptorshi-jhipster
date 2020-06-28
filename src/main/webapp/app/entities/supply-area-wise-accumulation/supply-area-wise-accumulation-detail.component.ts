import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyAreaWiseAccumulation } from 'app/shared/model/supply-area-wise-accumulation.model';

@Component({
    selector: 'jhi-supply-area-wise-accumulation-detail',
    templateUrl: './supply-area-wise-accumulation-detail.component.html'
})
export class SupplyAreaWiseAccumulationDetailComponent implements OnInit {
    supplyAreaWiseAccumulation: ISupplyAreaWiseAccumulation;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyAreaWiseAccumulation }) => {
            this.supplyAreaWiseAccumulation = supplyAreaWiseAccumulation;
        });
    }

    previousState() {
        window.history.back();
    }
}
