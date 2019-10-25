import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionService } from 'app/entities/dt-transaction';

type EntityResponseType = HttpResponse<IDtTransaction>;
type EntityArrayResponseType = HttpResponse<IDtTransaction[]>;

@Injectable({ providedIn: 'root' })
export class DtTransactionExtendedService extends DtTransactionService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/dt-transactions';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(dtTransaction: IDtTransaction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dtTransaction);
        return this.http
            .post<IDtTransaction>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dtTransaction: IDtTransaction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dtTransaction);
        return this.http
            .put<IDtTransaction>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
