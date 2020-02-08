import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';
import { CommercialProductInfoService } from 'app/entities/commercial-product-info';

type EntityResponseType = HttpResponse<ICommercialProductInfo>;
type EntityArrayResponseType = HttpResponse<ICommercialProductInfo[]>;

@Injectable({ providedIn: 'root' })
export class CommercialProductInfoExtendedService extends CommercialProductInfoService {
    public resourceUrl = SERVER_API_URL + 'api/extended/commercial-product-infos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-product-infos';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
