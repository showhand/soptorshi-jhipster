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
    constructor(
        protected financialAccountYearService: FinancialAccountYearExtendedService,
        protected activatedRoute: ActivatedRoute,
        protected financialAccountYearExtendedService: FinancialAccountYearExtendedService
    ) {
        super(financialAccountYearService, activatedRoute);
    }

    save() {
        this.isSaving = true;
        if (this.financialAccountYear.id !== undefined) {
            this.subscribeToSaveResponse(this.financialAccountYearExtendedService.update(this.financialAccountYear));
        } else {
            this.subscribeToSaveResponse(this.financialAccountYearExtendedService.create(this.financialAccountYear));
        }
    }
}
