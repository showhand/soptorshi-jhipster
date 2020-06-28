import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyAreaWiseAccumulation } from 'app/shared/model/supply-area-wise-accumulation.model';
import { SupplyAreaWiseAccumulationService } from 'app/entities/supply-area-wise-accumulation';

type EntityResponseType = HttpResponse<ISupplyAreaWiseAccumulation>;
type EntityArrayResponseType = HttpResponse<ISupplyAreaWiseAccumulation[]>;

@Injectable({ providedIn: 'root' })
export class SupplyAreaWiseAccumulationExtendedService extends SupplyAreaWiseAccumulationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-area-wise-accumulations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-area-wise-accumulations';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
