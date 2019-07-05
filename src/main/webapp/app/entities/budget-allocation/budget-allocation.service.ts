import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBudgetAllocation } from 'app/shared/model/budget-allocation.model';

type EntityResponseType = HttpResponse<IBudgetAllocation>;
type EntityArrayResponseType = HttpResponse<IBudgetAllocation[]>;

@Injectable({ providedIn: 'root' })
export class BudgetAllocationService {
    public resourceUrl = SERVER_API_URL + 'api/budget-allocations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/budget-allocations';

    public financialAccountYearId: number;
    public showSelect: boolean;
    public selectColumn: string;
    public detailsColumn: string;

    constructor(protected http: HttpClient) {}

    create(budgetAllocation: IBudgetAllocation): Observable<EntityResponseType> {
        return this.http.post<IBudgetAllocation>(this.resourceUrl, budgetAllocation, { observe: 'response' });
    }

    update(budgetAllocation: IBudgetAllocation): Observable<EntityResponseType> {
        return this.http.put<IBudgetAllocation>(this.resourceUrl, budgetAllocation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBudgetAllocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBudgetAllocation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBudgetAllocation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
