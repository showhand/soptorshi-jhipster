import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPoStatus } from 'app/shared/model/commercial-po-status.model';
import { CommercialPoStatusDetailComponent } from 'app/entities/commercial-po-status';

@Component({
    selector: 'jhi-commercial-po-status-detail-extended',
    templateUrl: './commercial-po-status-detail-extended.component.html'
})
export class CommercialPoStatusDetailExtendedComponent extends CommercialPoStatusDetailComponent {
    commercialPoStatus: ICommercialPoStatus;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPoStatus }) => {
            this.commercialPoStatus = commercialPoStatus;
        });
    }

    previousState() {
        window.history.back();
    }
}
