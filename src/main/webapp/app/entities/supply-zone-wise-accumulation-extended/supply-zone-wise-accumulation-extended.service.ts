import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';
import { SupplyZoneWiseAccumulationService } from 'app/entities/supply-zone-wise-accumulation';

type EntityResponseType = HttpResponse<ISupplyZoneWiseAccumulation>;
type EntityArrayResponseType = HttpResponse<ISupplyZoneWiseAccumulation[]>;

@Injectable({ providedIn: 'root' })
export class SupplyZoneWiseAccumulationExtendedService extends SupplyZoneWiseAccumulationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-zone-wise-accumulations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-zone-wise-accumulations';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
