import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyArea } from 'app/shared/model/supply-area.model';
import { SupplyAreaService } from 'app/entities/supply-area';

type EntityResponseType = HttpResponse<ISupplyArea>;
type EntityArrayResponseType = HttpResponse<ISupplyArea[]>;

@Injectable({ providedIn: 'root' })
export class SupplyAreaExtendedService extends SupplyAreaService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-areas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-areas';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
