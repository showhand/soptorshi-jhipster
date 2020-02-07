import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IStockStatus } from 'app/shared/model/stock-status.model';
import { StockStatusService } from 'app/entities/stock-status';

type EntityResponseType = HttpResponse<IStockStatus>;
type EntityArrayResponseType = HttpResponse<IStockStatus[]>;

@Injectable({ providedIn: 'root' })
export class StockStatusExtendedService extends StockStatusService {
    public resourceUrl = SERVER_API_URL + 'api/extended/stock-statuses';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/stock-statuses';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
