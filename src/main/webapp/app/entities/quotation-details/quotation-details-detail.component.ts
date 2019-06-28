import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IQuotationDetails } from 'app/shared/model/quotation-details.model';

@Component({
    selector: 'jhi-quotation-details-detail',
    templateUrl: './quotation-details-detail.component.html'
})
export class QuotationDetailsDetailComponent implements OnInit {
    quotationDetails: IQuotationDetails;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quotationDetails }) => {
            this.quotationDetails = quotationDetails;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
