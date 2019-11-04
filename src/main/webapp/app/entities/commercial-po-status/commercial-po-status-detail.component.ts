import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPoStatus } from 'app/shared/model/commercial-po-status.model';

@Component({
    selector: 'jhi-commercial-po-status-detail',
    templateUrl: './commercial-po-status-detail.component.html'
})
export class CommercialPoStatusDetailComponent implements OnInit {
    commercialPoStatus: ICommercialPoStatus;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPoStatus }) => {
            this.commercialPoStatus = commercialPoStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
