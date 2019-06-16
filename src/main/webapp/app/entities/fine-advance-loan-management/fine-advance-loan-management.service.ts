import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';

type EntityResponseType = HttpResponse<IFineAdvanceLoanManagement>;
type EntityArrayResponseType = HttpResponse<IFineAdvanceLoanManagement[]>;

@Injectable({ providedIn: 'root' })
export class FineAdvanceLoanManagementService {
    public resourceUrl = SERVER_API_URL + 'api/fine-advance-loan-managements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/fine-advance-loan-managements';

    constructor(protected http: HttpClient) {}

    create(fineAdvanceLoanManagement: IFineAdvanceLoanManagement): Observable<EntityResponseType> {
        return this.http.post<IFineAdvanceLoanManagement>(this.resourceUrl, fineAdvanceLoanManagement, { observe: 'response' });
    }

    update(fineAdvanceLoanManagement: IFineAdvanceLoanManagement): Observable<EntityResponseType> {
        return this.http.put<IFineAdvanceLoanManagement>(this.resourceUrl, fineAdvanceLoanManagement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFineAdvanceLoanManagement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFineAdvanceLoanManagement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFineAdvanceLoanManagement[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
