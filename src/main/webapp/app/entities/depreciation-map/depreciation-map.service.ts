import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepreciationMap } from 'app/shared/model/depreciation-map.model';

type EntityResponseType = HttpResponse<IDepreciationMap>;
type EntityArrayResponseType = HttpResponse<IDepreciationMap[]>;

@Injectable({ providedIn: 'root' })
export class DepreciationMapService {
    public resourceUrl = SERVER_API_URL + 'api/depreciation-maps';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/depreciation-maps';

    constructor(protected http: HttpClient) {}

    create(depreciationMap: IDepreciationMap): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(depreciationMap);
        return this.http
            .post<IDepreciationMap>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(depreciationMap: IDepreciationMap): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(depreciationMap);
        return this.http
            .put<IDepreciationMap>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDepreciationMap>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDepreciationMap[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDepreciationMap[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(depreciationMap: IDepreciationMap): IDepreciationMap {
        const copy: IDepreciationMap = Object.assign({}, depreciationMap, {
            createdOn: depreciationMap.createdOn != null && depreciationMap.createdOn.isValid() ? depreciationMap.createdOn.toJSON() : null,
            modifiedOn:
                depreciationMap.modifiedOn != null && depreciationMap.modifiedOn.isValid() ? depreciationMap.modifiedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((depreciationMap: IDepreciationMap) => {
                depreciationMap.createdOn = depreciationMap.createdOn != null ? moment(depreciationMap.createdOn) : null;
                depreciationMap.modifiedOn = depreciationMap.modifiedOn != null ? moment(depreciationMap.modifiedOn) : null;
            });
        }
        return res;
    }
}
