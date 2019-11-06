import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyOrderDetails } from 'app/shared/model/supply-order-details.model';

@Component({
    selector: 'jhi-supply-order-details-detail',
    templateUrl: './supply-order-details-detail.component.html'
})
export class SupplyOrderDetailsDetailComponent implements OnInit {
    supplyOrderDetails: ISupplyOrderDetails;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyOrderDetails }) => {
            this.supplyOrderDetails = supplyOrderDetails;
        });
    }

    previousState() {
        window.history.back();
    }
}
