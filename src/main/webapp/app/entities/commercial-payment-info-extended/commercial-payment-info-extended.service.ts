import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ICommercialPaymentInfo } from 'app/shared/model/commercial-payment-info.model';
import { CommercialPaymentInfoService } from 'app/entities/commercial-payment-info';

type EntityResponseType = HttpResponse<ICommercialPaymentInfo>;
type EntityArrayResponseType = HttpResponse<ICommercialPaymentInfo[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPaymentInfoExtendedService extends CommercialPaymentInfoService {
    public resourceUrl = SERVER_API_URL + 'api/extended/commercial-payment-infos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-payment-infos';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
