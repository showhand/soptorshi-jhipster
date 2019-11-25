import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalaryMessages } from 'app/shared/model/salary-messages.model';

type EntityResponseType = HttpResponse<ISalaryMessages>;
type EntityArrayResponseType = HttpResponse<ISalaryMessages[]>;

@Injectable({ providedIn: 'root' })
export class SalaryMessagesService {
    public resourceUrl = SERVER_API_URL + 'api/salary-messages';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/salary-messages';

    constructor(protected http: HttpClient) {}

    create(salaryMessages: ISalaryMessages): Observable<EntityResponseType> {
        return this.http.post<ISalaryMessages>(this.resourceUrl, salaryMessages, { observe: 'response' });
    }

    update(salaryMessages: ISalaryMessages): Observable<EntityResponseType> {
        return this.http.put<ISalaryMessages>(this.resourceUrl, salaryMessages, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISalaryMessages>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISalaryMessages[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISalaryMessages[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
