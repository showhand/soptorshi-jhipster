import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';
import { SystemGroupMapService } from 'app/entities/system-group-map';

type EntityResponseType = HttpResponse<ISystemGroupMap>;
type EntityArrayResponseType = HttpResponse<ISystemGroupMap[]>;

@Injectable({ providedIn: 'root' })
export class SystemGroupMapExtendedService extends SystemGroupMapService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/system-group-maps';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(systemGroupMap: ISystemGroupMap): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemGroupMap);
        return this.http
            .post<ISystemGroupMap>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(systemGroupMap: ISystemGroupMap): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemGroupMap);
        return this.http
            .put<ISystemGroupMap>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
