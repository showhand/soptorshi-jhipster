import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IQuotationDetails } from 'app/shared/model/quotation-details.model';
import { QuotationDetailsDetailComponent } from 'app/entities/quotation-details';

@Component({
    selector: 'jhi-quotation-details-extended-detail',
    templateUrl: './quotation-details-extended-detail.component.html'
})
export class QuotationDetailsExtendedDetailComponent extends QuotationDetailsDetailComponent implements OnInit {
    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {
        super(dataUtils, activatedRoute);
    }
}
