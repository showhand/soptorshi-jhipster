import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISystemGroupMap } from 'app/shared/model/system-group-map.model';

type EntityResponseType = HttpResponse<ISystemGroupMap>;
type EntityArrayResponseType = HttpResponse<ISystemGroupMap[]>;

@Injectable({ providedIn: 'root' })
export class SystemGroupMapService {
    public resourceUrl = SERVER_API_URL + 'api/system-group-maps';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/system-group-maps';

    constructor(protected http: HttpClient) {}

    create(systemGroupMap: ISystemGroupMap): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemGroupMap);
        return this.http
            .post<ISystemGroupMap>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(systemGroupMap: ISystemGroupMap): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemGroupMap);
        return this.http
            .put<ISystemGroupMap>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISystemGroupMap>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISystemGroupMap[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISystemGroupMap[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(systemGroupMap: ISystemGroupMap): ISystemGroupMap {
        const copy: ISystemGroupMap = Object.assign({}, systemGroupMap, {
            modifiedOn:
                systemGroupMap.modifiedOn != null && systemGroupMap.modifiedOn.isValid()
                    ? systemGroupMap.modifiedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((systemGroupMap: ISystemGroupMap) => {
                systemGroupMap.modifiedOn = systemGroupMap.modifiedOn != null ? moment(systemGroupMap.modifiedOn) : null;
            });
        }
        return res;
    }
}
