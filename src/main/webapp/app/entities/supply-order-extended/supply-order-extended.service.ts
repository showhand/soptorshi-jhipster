import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyOrder } from 'app/shared/model/supply-order.model';
import { SupplyOrderService } from 'app/entities/supply-order';

type EntityResponseType = HttpResponse<ISupplyOrder>;
type EntityArrayResponseType = HttpResponse<ISupplyOrder[]>;

@Injectable({ providedIn: 'root' })
export class SupplyOrderExtendedService extends SupplyOrderService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-orders';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-orders';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
