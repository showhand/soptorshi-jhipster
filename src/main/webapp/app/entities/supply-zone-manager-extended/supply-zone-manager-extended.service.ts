import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyZoneManager } from 'app/shared/model/supply-zone-manager.model';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager';

type EntityResponseType = HttpResponse<ISupplyZoneManager>;
type EntityArrayResponseType = HttpResponse<ISupplyZoneManager[]>;

@Injectable({ providedIn: 'root' })
export class SupplyZoneManagerExtendedService extends SupplyZoneManagerService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-zone-managers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-zone-managers';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
