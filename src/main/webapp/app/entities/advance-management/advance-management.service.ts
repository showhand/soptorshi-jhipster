import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdvanceManagement } from 'app/shared/model/advance-management.model';

type EntityResponseType = HttpResponse<IAdvanceManagement>;
type EntityArrayResponseType = HttpResponse<IAdvanceManagement[]>;

@Injectable({ providedIn: 'root' })
export class AdvanceManagementService {
    public resourceUrl = SERVER_API_URL + 'api/advance-managements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/advance-managements';

    constructor(protected http: HttpClient) {}

    create(advanceManagement: IAdvanceManagement): Observable<EntityResponseType> {
        return this.http.post<IAdvanceManagement>(this.resourceUrl, advanceManagement, { observe: 'response' });
    }

    update(advanceManagement: IAdvanceManagement): Observable<EntityResponseType> {
        return this.http.put<IAdvanceManagement>(this.resourceUrl, advanceManagement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAdvanceManagement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdvanceManagement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdvanceManagement[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
