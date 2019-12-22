import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { StockInItemService } from 'app/entities/stock-in-item';

type EntityResponseType = HttpResponse<IStockInItem>;
type EntityArrayResponseType = HttpResponse<IStockInItem[]>;

@Injectable({ providedIn: 'root' })
export class StockInItemExtendedService extends StockInItemService {
    public resourceUrl = SERVER_API_URL + 'api/extended/stock-in-items';
    public resourceSearchUrl = SERVER_API_URL + 'api/extended/_search/stock-in-items';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
