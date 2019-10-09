import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';
import { SystemAccountMapService } from 'app/entities/system-account-map';

type EntityResponseType = HttpResponse<ISystemAccountMap>;
type EntityArrayResponseType = HttpResponse<ISystemAccountMap[]>;

@Injectable({ providedIn: 'root' })
export class SystemAccountMapExtendedService extends SystemAccountMapService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/system-account-maps';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(systemAccountMap: ISystemAccountMap): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemAccountMap);
        return this.http
            .post<ISystemAccountMap>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(systemAccountMap: ISystemAccountMap): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemAccountMap);
        return this.http
            .put<ISystemAccountMap>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
