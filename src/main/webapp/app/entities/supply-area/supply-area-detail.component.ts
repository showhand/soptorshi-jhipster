import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyArea } from 'app/shared/model/supply-area.model';

@Component({
    selector: 'jhi-supply-area-detail',
    templateUrl: './supply-area-detail.component.html'
})
export class SupplyAreaDetailComponent implements OnInit {
    supplyArea: ISupplyArea;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyArea }) => {
            this.supplyArea = supplyArea;
        });
    }

    previousState() {
        window.history.back();
    }
}
