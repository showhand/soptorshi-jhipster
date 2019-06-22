import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseCommittee } from 'app/shared/model/purchase-committee.model';

type EntityResponseType = HttpResponse<IPurchaseCommittee>;
type EntityArrayResponseType = HttpResponse<IPurchaseCommittee[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseCommitteeService {
    public resourceUrl = SERVER_API_URL + 'api/purchase-committees';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/purchase-committees';

    constructor(protected http: HttpClient) {}

    create(purchaseCommittee: IPurchaseCommittee): Observable<EntityResponseType> {
        return this.http.post<IPurchaseCommittee>(this.resourceUrl, purchaseCommittee, { observe: 'response' });
    }

    update(purchaseCommittee: IPurchaseCommittee): Observable<EntityResponseType> {
        return this.http.put<IPurchaseCommittee>(this.resourceUrl, purchaseCommittee, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPurchaseCommittee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPurchaseCommittee[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPurchaseCommittee[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
