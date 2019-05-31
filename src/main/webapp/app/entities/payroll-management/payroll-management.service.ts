import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPayrollManagement } from 'app/shared/model/payroll-management.model';

type EntityResponseType = HttpResponse<IPayrollManagement>;
type EntityArrayResponseType = HttpResponse<IPayrollManagement[]>;

@Injectable({ providedIn: 'root' })
export class PayrollManagementService {
    public resourceUrl = SERVER_API_URL + 'api/payroll-managements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/payroll-managements';

    constructor(protected http: HttpClient) {}

    create(payrollManagement: IPayrollManagement): Observable<EntityResponseType> {
        return this.http.post<IPayrollManagement>(this.resourceUrl, payrollManagement, { observe: 'response' });
    }

    update(payrollManagement: IPayrollManagement): Observable<EntityResponseType> {
        return this.http.put<IPayrollManagement>(this.resourceUrl, payrollManagement, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPayrollManagement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPayrollManagement[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPayrollManagement[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
