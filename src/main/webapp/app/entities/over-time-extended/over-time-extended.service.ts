import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IOverTime } from 'app/shared/model/over-time.model';
import { OverTimeService } from 'app/entities/over-time';

type EntityResponseType = HttpResponse<IOverTime>;
type EntityArrayResponseType = HttpResponse<IOverTime[]>;

@Injectable({ providedIn: 'root' })
export class OverTimeExtendedService extends OverTimeService {
    public resourceUrl = SERVER_API_URL + 'api/extended/over-times';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/over-times';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
