import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyZone } from 'app/shared/model/supply-zone.model';
import { SupplyZoneService } from 'app/entities/supply-zone';

type EntityResponseType = HttpResponse<ISupplyZone>;
type EntityArrayResponseType = HttpResponse<ISupplyZone[]>;

@Injectable({ providedIn: 'root' })
export class SupplyZoneExtendedService extends SupplyZoneService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-zones';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-zones';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
