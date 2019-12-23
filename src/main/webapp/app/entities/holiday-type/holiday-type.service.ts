import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHolidayType } from 'app/shared/model/holiday-type.model';

type EntityResponseType = HttpResponse<IHolidayType>;
type EntityArrayResponseType = HttpResponse<IHolidayType[]>;

@Injectable({ providedIn: 'root' })
export class HolidayTypeService {
    public resourceUrl = SERVER_API_URL + 'api/holiday-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/holiday-types';

    constructor(protected http: HttpClient) {}

    create(holidayType: IHolidayType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(holidayType);
        return this.http
            .post<IHolidayType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(holidayType: IHolidayType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(holidayType);
        return this.http
            .put<IHolidayType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IHolidayType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHolidayType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IHolidayType[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(holidayType: IHolidayType): IHolidayType {
        const copy: IHolidayType = Object.assign({}, holidayType, {
            createdOn: holidayType.createdOn != null && holidayType.createdOn.isValid() ? holidayType.createdOn.toJSON() : null,
            updatedOn: holidayType.updatedOn != null && holidayType.updatedOn.isValid() ? holidayType.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((holidayType: IHolidayType) => {
                holidayType.createdOn = holidayType.createdOn != null ? moment(holidayType.createdOn) : null;
                holidayType.updatedOn = holidayType.updatedOn != null ? moment(holidayType.updatedOn) : null;
            });
        }
        return res;
    }
}
