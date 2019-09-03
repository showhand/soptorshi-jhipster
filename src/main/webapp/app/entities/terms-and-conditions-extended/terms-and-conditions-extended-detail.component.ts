import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITermsAndConditions } from 'app/shared/model/terms-and-conditions.model';
import { TermsAndConditionsDetailComponent } from 'app/entities/terms-and-conditions';

@Component({
    selector: 'jhi-terms-and-conditions-extended-detail',
    templateUrl: './terms-and-conditions-extended-detail.component.html'
})
export class TermsAndConditionsExtendedDetailComponent extends TermsAndConditionsDetailComponent implements OnInit {
    termsAndConditions: ITermsAndConditions;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}
