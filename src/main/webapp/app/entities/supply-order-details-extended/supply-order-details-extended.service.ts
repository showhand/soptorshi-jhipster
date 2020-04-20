import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyOrderDetails } from 'app/shared/model/supply-order-details.model';
import { SupplyOrderDetailsService } from 'app/entities/supply-order-details';

type EntityResponseType = HttpResponse<ISupplyOrderDetails>;
type EntityArrayResponseType = HttpResponse<ISupplyOrderDetails[]>;

@Injectable({ providedIn: 'root' })
export class SupplyOrderDetailsExtendedService extends SupplyOrderDetailsService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-order-details';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-order-details';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
