import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from 'app/entities/holiday-type';

type EntityResponseType = HttpResponse<IHolidayType>;
type EntityArrayResponseType = HttpResponse<IHolidayType[]>;

@Injectable({ providedIn: 'root' })
export class HolidayTypeExtendedService extends HolidayTypeService {
    public resourceUrl = SERVER_API_URL + 'api/holiday-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/holiday-types';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(holidayType: IHolidayType): Observable<EntityResponseType> {
        return this.http.post<IHolidayType>(this.resourceUrl, holidayType, { observe: 'response' });
    }

    update(holidayType: IHolidayType): Observable<EntityResponseType> {
        return this.http.put<IHolidayType>(this.resourceUrl, holidayType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHolidayType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHolidayType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHolidayType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
