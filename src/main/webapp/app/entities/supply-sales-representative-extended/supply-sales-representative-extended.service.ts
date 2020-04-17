import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';
import { SupplySalesRepresentativeService } from 'app/entities/supply-sales-representative';

type EntityResponseType = HttpResponse<ISupplySalesRepresentative>;
type EntityArrayResponseType = HttpResponse<ISupplySalesRepresentative[]>;

@Injectable({ providedIn: 'root' })
export class SupplySalesRepresentativeExtendedService extends SupplySalesRepresentativeService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-sales-representatives';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-sales-representatives';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
