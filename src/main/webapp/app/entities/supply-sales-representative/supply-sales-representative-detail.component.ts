import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';

@Component({
    selector: 'jhi-supply-sales-representative-detail',
    templateUrl: './supply-sales-representative-detail.component.html'
})
export class SupplySalesRepresentativeDetailComponent implements OnInit {
    supplySalesRepresentative: ISupplySalesRepresentative;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ supplySalesRepresentative }) => {
            this.supplySalesRepresentative = supplySalesRepresentative;
        });
    }

    previousState() {
        window.history.back();
    }
}
