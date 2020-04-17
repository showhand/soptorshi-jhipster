import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyShop } from 'app/shared/model/supply-shop.model';
import { SupplyShopService } from 'app/entities/supply-shop';

type EntityResponseType = HttpResponse<ISupplyShop>;
type EntityArrayResponseType = HttpResponse<ISupplyShop[]>;

@Injectable({ providedIn: 'root' })
export class SupplyShopExtendedService extends SupplyShopService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-shops';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-shops';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
