import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearExtendedService } from './financial-account-year-extended.service';
import { FinancialAccountYearUpdateComponent } from 'app/entities/financial-account-year';

@Component({
    selector: 'jhi-financial-account-year-update',
    templateUrl: './financial-account-year-extended-update.component.html'
})
export class FinancialAccountYearExtendedUpdateComponent extends FinancialAccountYearUpdateComponent implements OnInit {
    constructor(protected financialAccountYearService: FinancialAccountYearExtendedService, protected activatedRoute: ActivatedRoute) {
        super(financialAccountYearService, activatedRoute);
    }
}
