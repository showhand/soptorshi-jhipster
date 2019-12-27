import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CommercialPoDetailComponent } from 'app/entities/commercial-po';

@Component({
    selector: 'jhi-commercial-po-detail-extended',
    templateUrl: './commercial-po-detail-extended.component.html'
})
export class CommercialPoDetailExtendedComponent extends CommercialPoDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
