import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMontlySalaryVouchers } from 'app/shared/model/montly-salary-vouchers.model';

type EntityResponseType = HttpResponse<IMontlySalaryVouchers>;
type EntityArrayResponseType = HttpResponse<IMontlySalaryVouchers[]>;

@Injectable({ providedIn: 'root' })
export class MontlySalaryVouchersService {
    public resourceUrl = SERVER_API_URL + 'api/montly-salary-vouchers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/montly-salary-vouchers';

    constructor(protected http: HttpClient) {}

    create(montlySalaryVouchers: IMontlySalaryVouchers): Observable<EntityResponseType> {
        return this.http.post<IMontlySalaryVouchers>(this.resourceUrl, montlySalaryVouchers, { observe: 'response' });
    }

    update(montlySalaryVouchers: IMontlySalaryVouchers): Observable<EntityResponseType> {
        return this.http.put<IMontlySalaryVouchers>(this.resourceUrl, montlySalaryVouchers, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMontlySalaryVouchers>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMontlySalaryVouchers[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMontlySalaryVouchers[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
