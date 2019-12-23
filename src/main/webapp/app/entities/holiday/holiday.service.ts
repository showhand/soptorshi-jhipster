import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHoliday } from 'app/shared/model/holiday.model';

type EntityResponseType = HttpResponse<IHoliday>;
type EntityArrayResponseType = HttpResponse<IHoliday[]>;

@Injectable({ providedIn: 'root' })
export class HolidayService {
    public resourceUrl = SERVER_API_URL + 'api/holidays';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/holidays';

    constructor(protected http: HttpClient) {}

    create(holiday: IHoliday): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(holiday);
        return this.http
            .post<IHoliday>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(holiday: IHoliday): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(holiday);
        return this.http
            .put<IHoliday>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHoliday>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHoliday[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHoliday[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(holiday: IHoliday): IHoliday {
        const copy: IHoliday = Object.assign({}, holiday, {
            fromDate: holiday.fromDate != null && holiday.fromDate.isValid() ? holiday.fromDate.format(DATE_FORMAT) : null,
            toDate: holiday.toDate != null && holiday.toDate.isValid() ? holiday.toDate.format(DATE_FORMAT) : null,
            createdOn: holiday.createdOn != null && holiday.createdOn.isValid() ? holiday.createdOn.toJSON() : null,
            updatedOn: holiday.updatedOn != null && holiday.updatedOn.isValid() ? holiday.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fromDate = res.body.fromDate != null ? moment(res.body.fromDate) : null;
            res.body.toDate = res.body.toDate != null ? moment(res.body.toDate) : null;
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((holiday: IHoliday) => {
                holiday.fromDate = holiday.fromDate != null ? moment(holiday.fromDate) : null;
                holiday.toDate = holiday.toDate != null ? moment(holiday.toDate) : null;
                holiday.createdOn = holiday.createdOn != null ? moment(holiday.createdOn) : null;
                holiday.updatedOn = holiday.updatedOn != null ? moment(holiday.updatedOn) : null;
            });
        }
        return res;
    }
}
