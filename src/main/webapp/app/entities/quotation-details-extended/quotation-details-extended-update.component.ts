import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IQuotationDetails } from 'app/shared/model/quotation-details.model';
import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationService } from 'app/entities/quotation';
import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { RequisitionDetailsService } from 'app/entities/requisition-details';
import { QuotationDetailsService, QuotationDetailsUpdateComponent } from 'app/entities/quotation-details';

@Component({
    selector: 'jhi-quotation-details-extended-update',
    templateUrl: './quotation-details-extended-update.component.html'
})
export class QuotationDetailsExtendedUpdateComponent extends QuotationDetailsUpdateComponent implements OnInit {
    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected quotationDetailsService: QuotationDetailsService,
        protected quotationService: QuotationService,
        protected requisitionDetailsService: RequisitionDetailsService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(dataUtils, jhiAlertService, quotationDetailsService, quotationService, requisitionDetailsService, activatedRoute);
    }
}
