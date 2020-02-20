import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IProduction } from 'app/shared/model/production.model';
import { ProductionService } from 'app/entities/production';

type EntityResponseType = HttpResponse<IProduction>;
type EntityArrayResponseType = HttpResponse<IProduction[]>;

@Injectable({ providedIn: 'root' })
export class ProductionExtendedService extends ProductionService {
    public resourceUrl = SERVER_API_URL + 'api/extended/productions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/productions';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
