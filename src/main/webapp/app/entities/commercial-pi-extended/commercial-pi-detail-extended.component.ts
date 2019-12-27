import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CommercialPiDetailComponent } from 'app/entities/commercial-pi';

@Component({
    selector: 'jhi-commercial-pi-detail-extended',
    templateUrl: './commercial-pi-detail-extended.component.html'
})
export class CommercialPiDetailExtendedComponent extends CommercialPiDetailComponent {
    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }
}
