import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDesignation } from 'app/shared/model/designation.model';

type EntityResponseType = HttpResponse<IDesignation>;
type EntityArrayResponseType = HttpResponse<IDesignation[]>;

@Injectable({ providedIn: 'root' })
export class DesignationService {
    public resourceUrl = SERVER_API_URL + 'api/designations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/designations';

    constructor(protected http: HttpClient) {}

    create(designation: IDesignation): Observable<EntityResponseType> {
        return this.http.post<IDesignation>(this.resourceUrl, designation, { observe: 'response' });
    }

    update(designation: IDesignation): Observable<EntityResponseType> {
        return this.http.put<IDesignation>(this.resourceUrl, designation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDesignation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDesignation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDesignation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
