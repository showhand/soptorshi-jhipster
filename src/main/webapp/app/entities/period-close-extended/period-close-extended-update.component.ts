import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPeriodClose } from 'app/shared/model/period-close.model';
import { PeriodCloseExtendedService } from './period-close-extended.service';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';
import { PeriodCloseUpdateComponent } from 'app/entities/period-close';

@Component({
    selector: 'jhi-period-close-update',
    templateUrl: './period-close-extended-update.component.html'
})
export class PeriodCloseExtendedUpdateComponent extends PeriodCloseUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected periodCloseService: PeriodCloseExtendedService,
        protected financialAccountYearService: FinancialAccountYearService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, periodCloseService, financialAccountYearService, activatedRoute);
    }
}
