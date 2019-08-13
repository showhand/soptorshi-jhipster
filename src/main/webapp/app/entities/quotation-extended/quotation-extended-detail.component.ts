import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationDetailComponent } from 'app/entities/quotation';

@Component({
    selector: 'jhi-quotation-extended-detail',
    templateUrl: './quotation-extended-detail.component.html'
})
export class QuotationExtendedDetailComponent extends QuotationDetailComponent {}
