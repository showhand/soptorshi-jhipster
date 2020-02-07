import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { StockInProcessService } from 'app/entities/stock-in-process';

@Injectable({ providedIn: 'root' })
export class StockInProcessExtendedService extends StockInProcessService {
    public resourceUrl = SERVER_API_URL + 'api/extended/stock-in-processes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/stock-in-processes';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
