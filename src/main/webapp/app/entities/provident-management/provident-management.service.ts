import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProvidentManagement } from 'app/shared/model/provident-management.model';

type EntityResponseType = HttpResponse<IProvidentManagement>;
type EntityArrayResponseType = HttpResponse<IProvidentManagement[]>;

@Injectable({ providedIn: 'root' })
export class ProvidentManagementService {
    public resourceUrl = SERVER_API_URL + 'api/provident-managements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/provident-managements';

    constructor(protected http: HttpClient) {}

    create(providentManagement: IProvidentManagement): Observable<EntityResponseType> {
        return this.http.post<IProvidentManagement>(this.resourceUrl, providentManagement, { observe: 'response' });
    }

    update(providentManagement: IProvidentManagement): Observable<EntityResponseType> {
        return this.http.put<IProvidentManagement>(this.resourceUrl, providentManagement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProvidentManagement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProvidentManagement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProvidentManagement[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
