import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMstGroup } from 'app/shared/model/mst-group.model';
import { MstGroupService } from 'app/entities/mst-group';

type EntityResponseType = HttpResponse<IMstGroup>;
type EntityArrayResponseType = HttpResponse<IMstGroup[]>;

@Injectable({ providedIn: 'root' })
export class MstGroupExtendedService extends MstGroupService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/mst-groups';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(mstGroup: IMstGroup): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(mstGroup);
        return this.http
            .post<IMstGroup>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(mstGroup: IMstGroup): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(mstGroup);
        return this.http
            .put<IMstGroup>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
