import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWeekend } from 'app/shared/model/weekend.model';

type EntityResponseType = HttpResponse<IWeekend>;
type EntityArrayResponseType = HttpResponse<IWeekend[]>;

@Injectable({ providedIn: 'root' })
export class WeekendService {
    public resourceUrl = SERVER_API_URL + 'api/weekends';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/weekends';

    constructor(protected http: HttpClient) {}

    create(weekend: IWeekend): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(weekend);
        return this.http
            .post<IWeekend>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(weekend: IWeekend): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(weekend);
        return this.http
            .put<IWeekend>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWeekend>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWeekend[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWeekend[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(weekend: IWeekend): IWeekend {
        const copy: IWeekend = Object.assign({}, weekend, {
            activeFrom: weekend.activeFrom != null && weekend.activeFrom.isValid() ? weekend.activeFrom.format(DATE_FORMAT) : null,
            activeTo: weekend.activeTo != null && weekend.activeTo.isValid() ? weekend.activeTo.format(DATE_FORMAT) : null,
            createdOn: weekend.createdOn != null && weekend.createdOn.isValid() ? weekend.createdOn.toJSON() : null,
            updatedOn: weekend.updatedOn != null && weekend.updatedOn.isValid() ? weekend.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.activeFrom = res.body.activeFrom != null ? moment(res.body.activeFrom) : null;
            res.body.activeTo = res.body.activeTo != null ? moment(res.body.activeTo) : null;
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((weekend: IWeekend) => {
                weekend.activeFrom = weekend.activeFrom != null ? moment(weekend.activeFrom) : null;
                weekend.activeTo = weekend.activeTo != null ? moment(weekend.activeTo) : null;
                weekend.createdOn = weekend.createdOn != null ? moment(weekend.createdOn) : null;
                weekend.updatedOn = weekend.updatedOn != null ? moment(weekend.updatedOn) : null;
            });
        }
        return res;
    }
}
