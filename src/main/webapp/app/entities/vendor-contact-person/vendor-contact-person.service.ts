import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVendorContactPerson } from 'app/shared/model/vendor-contact-person.model';

type EntityResponseType = HttpResponse<IVendorContactPerson>;
type EntityArrayResponseType = HttpResponse<IVendorContactPerson[]>;

@Injectable({ providedIn: 'root' })
export class VendorContactPersonService {
    public resourceUrl = SERVER_API_URL + 'api/vendor-contact-people';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/vendor-contact-people';

    constructor(protected http: HttpClient) {}

    create(vendorContactPerson: IVendorContactPerson): Observable<EntityResponseType> {
        return this.http.post<IVendorContactPerson>(this.resourceUrl, vendorContactPerson, { observe: 'response' });
    }

    update(vendorContactPerson: IVendorContactPerson): Observable<EntityResponseType> {
        return this.http.put<IVendorContactPerson>(this.resourceUrl, vendorContactPerson, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVendorContactPerson>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVendorContactPerson[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVendorContactPerson[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
