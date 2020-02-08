import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationService } from 'app/entities/inventory-location';

type EntityResponseType = HttpResponse<IInventoryLocation>;
type EntityArrayResponseType = HttpResponse<IInventoryLocation[]>;

@Injectable({ providedIn: 'root' })
export class InventoryLocationExtendedService extends InventoryLocationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/inventory-locations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/inventory-locations';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
