import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IQuotation } from 'app/shared/model/quotation.model';

@Component({
    selector: 'jhi-quotation-detail',
    templateUrl: './quotation-detail.component.html'
})
export class QuotationDetailComponent implements OnInit {
    quotation: IQuotation;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quotation }) => {
            this.quotation = quotation;
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
