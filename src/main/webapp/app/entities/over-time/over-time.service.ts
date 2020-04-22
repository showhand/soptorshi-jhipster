import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOverTime } from 'app/shared/model/over-time.model';

type EntityResponseType = HttpResponse<IOverTime>;
type EntityArrayResponseType = HttpResponse<IOverTime[]>;

@Injectable({ providedIn: 'root' })
export class OverTimeService {
    public resourceUrl = SERVER_API_URL + 'api/over-times';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/over-times';

    constructor(protected http: HttpClient) {}

    create(overTime: IOverTime): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(overTime);
        return this.http
            .post<IOverTime>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(overTime: IOverTime): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(overTime);
        return this.http
            .put<IOverTime>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOverTime>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOverTime[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOverTime[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(overTime: IOverTime): IOverTime {
        const copy: IOverTime = Object.assign({}, overTime, {
            overTimeDate:
                overTime.overTimeDate != null && overTime.overTimeDate.isValid() ? overTime.overTimeDate.format(DATE_FORMAT) : null,
            fromTime: overTime.fromTime != null && overTime.fromTime.isValid() ? overTime.fromTime.toJSON() : null,
            toTime: overTime.toTime != null && overTime.toTime.isValid() ? overTime.toTime.toJSON() : null,
            createdOn: overTime.createdOn != null && overTime.createdOn.isValid() ? overTime.createdOn.toJSON() : null,
            updatedOn: overTime.updatedOn != null && overTime.updatedOn.isValid() ? overTime.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.overTimeDate = res.body.overTimeDate != null ? moment(res.body.overTimeDate) : null;
            res.body.fromTime = res.body.fromTime != null ? moment(res.body.fromTime) : null;
            res.body.toTime = res.body.toTime != null ? moment(res.body.toTime) : null;
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((overTime: IOverTime) => {
                overTime.overTimeDate = overTime.overTimeDate != null ? moment(overTime.overTimeDate) : null;
                overTime.fromTime = overTime.fromTime != null ? moment(overTime.fromTime) : null;
                overTime.toTime = overTime.toTime != null ? moment(overTime.toTime) : null;
                overTime.createdOn = overTime.createdOn != null ? moment(overTime.createdOn) : null;
                overTime.updatedOn = overTime.updatedOn != null ? moment(overTime.updatedOn) : null;
            });
        }
        return res;
    }
}
