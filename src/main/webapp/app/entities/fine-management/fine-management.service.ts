import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFineManagement } from 'app/shared/model/fine-management.model';

type EntityResponseType = HttpResponse<IFineManagement>;
type EntityArrayResponseType = HttpResponse<IFineManagement[]>;

@Injectable({ providedIn: 'root' })
export class FineManagementService {
    public resourceUrl = SERVER_API_URL + 'api/fine-managements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/fine-managements';

    constructor(protected http: HttpClient) {}

    create(fineManagement: IFineManagement): Observable<EntityResponseType> {
        return this.http.post<IFineManagement>(this.resourceUrl, fineManagement, { observe: 'response' });
    }

    update(fineManagement: IFineManagement): Observable<EntityResponseType> {
        return this.http.put<IFineManagement>(this.resourceUrl, fineManagement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFineManagement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFineManagement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFineManagement[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
