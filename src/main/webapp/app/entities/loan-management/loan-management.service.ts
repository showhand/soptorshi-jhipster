import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILoanManagement } from 'app/shared/model/loan-management.model';

type EntityResponseType = HttpResponse<ILoanManagement>;
type EntityArrayResponseType = HttpResponse<ILoanManagement[]>;

@Injectable({ providedIn: 'root' })
export class LoanManagementService {
    public resourceUrl = SERVER_API_URL + 'api/loan-managements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/loan-managements';

    constructor(protected http: HttpClient) {}

    create(loanManagement: ILoanManagement): Observable<EntityResponseType> {
        return this.http.post<ILoanManagement>(this.resourceUrl, loanManagement, { observe: 'response' });
    }

    update(loanManagement: ILoanManagement): Observable<EntityResponseType> {
        return this.http.put<ILoanManagement>(this.resourceUrl, loanManagement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILoanManagement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILoanManagement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILoanManagement[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
