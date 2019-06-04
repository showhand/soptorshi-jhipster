import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAllowanceManagement } from 'app/shared/model/allowance-management.model';

type EntityResponseType = HttpResponse<IAllowanceManagement>;
type EntityArrayResponseType = HttpResponse<IAllowanceManagement[]>;

@Injectable({ providedIn: 'root' })
export class AllowanceManagementService {
    public resourceUrl = SERVER_API_URL + 'api/allowance-managements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/allowance-managements';

    constructor(protected http: HttpClient) {}

    create(allowanceManagement: IAllowanceManagement): Observable<EntityResponseType> {
        return this.http.post<IAllowanceManagement>(this.resourceUrl, allowanceManagement, { observe: 'response' });
    }

    update(allowanceManagement: IAllowanceManagement): Observable<EntityResponseType> {
        return this.http.put<IAllowanceManagement>(this.resourceUrl, allowanceManagement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAllowanceManagement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAllowanceManagement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAllowanceManagement[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
