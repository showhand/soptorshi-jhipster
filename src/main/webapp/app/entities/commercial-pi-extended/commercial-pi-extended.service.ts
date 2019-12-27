import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ICommercialPi } from 'app/shared/model/commercial-pi.model';
import { CommercialPiService } from 'app/entities/commercial-pi';

type EntityResponseType = HttpResponse<ICommercialPi>;
type EntityArrayResponseType = HttpResponse<ICommercialPi[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPiExtendedService extends CommercialPiService {
    public resourceUrl = SERVER_API_URL + 'api/extended/commercial-pis';
    public resourceSearchUrl = SERVER_API_URL + 'api/extended/_search/commercial-pis';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
