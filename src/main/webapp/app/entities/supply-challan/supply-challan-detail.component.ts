import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplyChallan } from 'app/shared/model/supply-challan.model';

@Component({
    selector: 'jhi-supply-challan-detail',
    templateUrl: './supply-challan-detail.component.html'
})
export class SupplyChallanDetailComponent implements OnInit {
    supplyChallan: ISupplyChallan;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplyChallan }) => {
            this.supplyChallan = supplyChallan;
        });
    }

    previousState() {
        window.history.back();
    }
}
