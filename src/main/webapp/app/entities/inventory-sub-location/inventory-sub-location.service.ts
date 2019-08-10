import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInventorySubLocation } from 'app/shared/model/inventory-sub-location.model';

type EntityResponseType = HttpResponse<IInventorySubLocation>;
type EntityArrayResponseType = HttpResponse<IInventorySubLocation[]>;

@Injectable({ providedIn: 'root' })
export class InventorySubLocationService {
    public resourceUrl = SERVER_API_URL + 'api/inventory-sub-locations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/inventory-sub-locations';

    constructor(protected http: HttpClient) {}

    create(inventorySubLocation: IInventorySubLocation): Observable<EntityResponseType> {
        return this.http.post<IInventorySubLocation>(this.resourceUrl, inventorySubLocation, { observe: 'response' });
    }

    update(inventorySubLocation: IInventorySubLocation): Observable<EntityResponseType> {
        return this.http.put<IInventorySubLocation>(this.resourceUrl, inventorySubLocation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IInventorySubLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInventorySubLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInventorySubLocation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
