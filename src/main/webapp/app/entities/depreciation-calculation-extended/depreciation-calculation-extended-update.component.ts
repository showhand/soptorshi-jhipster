import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { DepreciationCalculationExtendedService } from './depreciation-calculation-extended.service';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';
import { DepreciationCalculationUpdateComponent } from 'app/entities/depreciation-calculation';

@Component({
    selector: 'jhi-depreciation-calculation-update',
    templateUrl: './depreciation-calculation-extended-update.component.html'
})
export class DepreciationCalculationExtendedUpdateComponent extends DepreciationCalculationUpdateComponent implements OnInit {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected depreciationCalculationService: DepreciationCalculationExtendedService,
        protected financialAccountYearService: FinancialAccountYearService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, depreciationCalculationService, financialAccountYearService, activatedRoute);
    }
}
