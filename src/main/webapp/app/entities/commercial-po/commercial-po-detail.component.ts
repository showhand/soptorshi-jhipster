import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPo } from 'app/shared/model/commercial-po.model';

@Component({
    selector: 'jhi-commercial-po-detail',
    templateUrl: './commercial-po-detail.component.html'
})
export class CommercialPoDetailComponent implements OnInit {
    commercialPo: ICommercialPo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPo }) => {
            this.commercialPo = commercialPo;
        });
    }

    previousState() {
        window.history.back();
    }
}
