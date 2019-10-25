import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';

type EntityResponseType = HttpResponse<ICurrency>;
type EntityArrayResponseType = HttpResponse<ICurrency[]>;

@Injectable({ providedIn: 'root' })
export class CurrencyExtendedService extends CurrencyService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/currencies';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/currencies';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(currency: ICurrency): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(currency);
        return this.http
            .post<ICurrency>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(currency: ICurrency): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(currency);
        return this.http
            .put<ICurrency>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
