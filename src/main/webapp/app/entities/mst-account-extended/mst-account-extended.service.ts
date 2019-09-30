import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMstAccount } from 'app/shared/model/mst-account.model';
import { MstAccountService } from 'app/entities/mst-account';

type EntityResponseType = HttpResponse<IMstAccount>;
type EntityArrayResponseType = HttpResponse<IMstAccount[]>;

@Injectable({ providedIn: 'root' })
export class MstAccountExtendedService extends MstAccountService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/mst-accounts';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(mstAccount: IMstAccount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(mstAccount);
        return this.http
            .post<IMstAccount>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(mstAccount: IMstAccount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(mstAccount);
        return this.http
            .put<IMstAccount>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
