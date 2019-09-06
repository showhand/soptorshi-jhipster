import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVoucherNumberControl } from 'app/shared/model/voucher-number-control.model';

type EntityResponseType = HttpResponse<IVoucherNumberControl>;
type EntityArrayResponseType = HttpResponse<IVoucherNumberControl[]>;

@Injectable({ providedIn: 'root' })
export class VoucherNumberControlService {
    public resourceUrl = SERVER_API_URL + 'api/voucher-number-controls';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/voucher-number-controls';

    constructor(protected http: HttpClient) {}

    create(voucherNumberControl: IVoucherNumberControl): Observable<EntityResponseType> {
        return this.http.post<IVoucherNumberControl>(this.resourceUrl, voucherNumberControl, { observe: 'response' });
    }

    update(voucherNumberControl: IVoucherNumberControl): Observable<EntityResponseType> {
        return this.http.put<IVoucherNumberControl>(this.resourceUrl, voucherNumberControl, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVoucherNumberControl>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVoucherNumberControl[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVoucherNumberControl[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
