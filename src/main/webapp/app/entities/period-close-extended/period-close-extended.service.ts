import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPeriodClose } from 'app/shared/model/period-close.model';
import { PeriodCloseService } from 'app/entities/period-close';

type EntityResponseType = HttpResponse<IPeriodClose>;
type EntityArrayResponseType = HttpResponse<IPeriodClose[]>;

@Injectable({ providedIn: 'root' })
export class PeriodCloseExtendedService extends PeriodCloseService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/period-closes';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(periodClose: IPeriodClose): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(periodClose);
        return this.http
            .post<IPeriodClose>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(periodClose: IPeriodClose): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(periodClose);
        return this.http
            .put<IPeriodClose>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
