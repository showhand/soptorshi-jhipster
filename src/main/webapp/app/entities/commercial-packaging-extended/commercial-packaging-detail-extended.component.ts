import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';
import { CommercialPackagingDetailComponent } from 'app/entities/commercial-packaging';

@Component({
    selector: 'jhi-commercial-packaging-detail-extended',
    templateUrl: './commercial-packaging-detail-extended.component.html'
})
export class CommercialPackagingDetailExtendedComponent extends CommercialPackagingDetailComponent {
    commercialPackaging: ICommercialPackaging;

    constructor(protected activatedRoute: ActivatedRoute) {
        super(activatedRoute);
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commercialPackaging }) => {
            this.commercialPackaging = commercialPackaging;
        });
    }

    previousState() {
        window.history.back();
    }
}
