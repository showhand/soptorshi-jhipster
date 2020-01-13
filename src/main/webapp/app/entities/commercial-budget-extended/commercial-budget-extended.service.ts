import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';
import { CommercialBudgetService } from 'app/entities/commercial-budget';

type EntityResponseType = HttpResponse<ICommercialBudget>;
type EntityArrayResponseType = HttpResponse<ICommercialBudget[]>;

@Injectable({ providedIn: 'root' })
export class CommercialBudgetExtendedService extends CommercialBudgetService {
    public resourceUrl = SERVER_API_URL + 'api/extended/commercial-budgets';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-budgets';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
