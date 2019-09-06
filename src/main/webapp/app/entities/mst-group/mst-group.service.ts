import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMstGroup } from 'app/shared/model/mst-group.model';

type EntityResponseType = HttpResponse<IMstGroup>;
type EntityArrayResponseType = HttpResponse<IMstGroup[]>;

@Injectable({ providedIn: 'root' })
export class MstGroupService {
    public resourceUrl = SERVER_API_URL + 'api/mst-groups';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/mst-groups';

    constructor(protected http: HttpClient) {}

    create(mstGroup: IMstGroup): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(mstGroup);
        return this.http
            .post<IMstGroup>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(mstGroup: IMstGroup): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(mstGroup);
        return this.http
            .put<IMstGroup>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMstGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMstGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMstGroup[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(mstGroup: IMstGroup): IMstGroup {
        const copy: IMstGroup = Object.assign({}, mstGroup, {
            modifiedOn: mstGroup.modifiedOn != null && mstGroup.modifiedOn.isValid() ? mstGroup.modifiedOn.format(DATE_FORMAT) : null
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
            res.body.forEach((mstGroup: IMstGroup) => {
                mstGroup.modifiedOn = mstGroup.modifiedOn != null ? moment(mstGroup.modifiedOn) : null;
            });
        }
        return res;
    }
}
