import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISystemAccountMap } from 'app/shared/model/system-account-map.model';

type EntityResponseType = HttpResponse<ISystemAccountMap>;
type EntityArrayResponseType = HttpResponse<ISystemAccountMap[]>;

@Injectable({ providedIn: 'root' })
export class SystemAccountMapService {
    public resourceUrl = SERVER_API_URL + 'api/system-account-maps';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/system-account-maps';

    constructor(protected http: HttpClient) {}

    create(systemAccountMap: ISystemAccountMap): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemAccountMap);
        return this.http
            .post<ISystemAccountMap>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(systemAccountMap: ISystemAccountMap): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(systemAccountMap);
        return this.http
            .put<ISystemAccountMap>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISystemAccountMap>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISystemAccountMap[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISystemAccountMap[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(systemAccountMap: ISystemAccountMap): ISystemAccountMap {
        const copy: ISystemAccountMap = Object.assign({}, systemAccountMap, {
            modifiedOn:
                systemAccountMap.modifiedOn != null && systemAccountMap.modifiedOn.isValid()
                    ? systemAccountMap.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((systemAccountMap: ISystemAccountMap) => {
                systemAccountMap.modifiedOn = systemAccountMap.modifiedOn != null ? moment(systemAccountMap.modifiedOn) : null;
            });
        }
        return res;
    }
}
