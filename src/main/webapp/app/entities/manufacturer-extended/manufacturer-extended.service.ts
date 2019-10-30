import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerService } from 'app/entities/manufacturer';

type EntityResponseType = HttpResponse<IManufacturer>;
type EntityArrayResponseType = HttpResponse<IManufacturer[]>;

@Injectable({ providedIn: 'root' })
export class ManufacturerExtendedService extends ManufacturerService {
    public resourceUrl = SERVER_API_URL + 'api/manufacturers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/manufacturers';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(manufacturer: IManufacturer): Observable<EntityResponseType> {
        return this.http.post<IManufacturer>(this.resourceUrl, manufacturer, { observe: 'response' });
    }

    update(manufacturer: IManufacturer): Observable<EntityResponseType> {
        return this.http.put<IManufacturer>(this.resourceUrl, manufacturer, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IManufacturer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IManufacturer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IManufacturer[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
