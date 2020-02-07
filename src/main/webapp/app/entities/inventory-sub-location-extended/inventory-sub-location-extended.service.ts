import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location';

type EntityResponseType = HttpResponse<IInventorySubLocation>;
type EntityArrayResponseType = HttpResponse<IInventorySubLocation[]>;

@Injectable({ providedIn: 'root' })
export class InventorySubLocationExtendedService extends InventorySubLocationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/inventory-sub-locations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/inventory-sub-locations';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
