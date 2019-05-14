import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';

type EntityResponseType = HttpResponse<IMonthlySalary>;
type EntityArrayResponseType = HttpResponse<IMonthlySalary[]>;

@Injectable({ providedIn: 'root' })
export class MonthlySalaryService {
    public resourceUrl = SERVER_API_URL + 'api/monthly-salaries';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/monthly-salaries';

    constructor(protected http: HttpClient) {}

    create(monthlySalary: IMonthlySalary): Observable<EntityResponseType> {
        return this.http.post<IMonthlySalary>(this.resourceUrl, monthlySalary, { observe: 'response' });
    }

    update(monthlySalary: IMonthlySalary): Observable<EntityResponseType> {
        return this.http.put<IMonthlySalary>(this.resourceUrl, monthlySalary, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMonthlySalary>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMonthlySalary[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMonthlySalary[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
