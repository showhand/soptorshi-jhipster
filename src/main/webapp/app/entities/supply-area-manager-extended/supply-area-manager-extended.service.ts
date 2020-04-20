import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyAreaManager } from 'app/shared/model/supply-area-manager.model';
import { SupplyAreaManagerService } from 'app/entities/supply-area-manager';

type EntityResponseType = HttpResponse<ISupplyAreaManager>;
type EntityArrayResponseType = HttpResponse<ISupplyAreaManager[]>;

@Injectable({ providedIn: 'root' })
export class SupplyAreaManagerExtendedService extends SupplyAreaManagerService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-area-managers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-area-managers';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
