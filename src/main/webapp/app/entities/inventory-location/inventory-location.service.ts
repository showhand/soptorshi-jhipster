import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';

type EntityResponseType = HttpResponse<IInventoryLocation>;
type EntityArrayResponseType = HttpResponse<IInventoryLocation[]>;

@Injectable({ providedIn: 'root' })
export class InventoryLocationService {
    public resourceUrl = SERVER_API_URL + 'api/inventory-locations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/inventory-locations';

    constructor(protected http: HttpClient) {}

    create(inventoryLocation: IInventoryLocation): Observable<EntityResponseType> {
        return this.http.post<IInventoryLocation>(this.resourceUrl, inventoryLocation, { observe: 'response' });
    }

    update(inventoryLocation: IInventoryLocation): Observable<EntityResponseType> {
        return this.http.put<IInventoryLocation>(this.resourceUrl, inventoryLocation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IInventoryLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInventoryLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInventoryLocation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
