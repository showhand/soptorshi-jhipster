import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepartmentHead } from 'app/shared/model/department-head.model';

type EntityResponseType = HttpResponse<IDepartmentHead>;
type EntityArrayResponseType = HttpResponse<IDepartmentHead[]>;

@Injectable({ providedIn: 'root' })
export class DepartmentHeadService {
    public resourceUrl = SERVER_API_URL + 'api/department-heads';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/department-heads';

    constructor(protected http: HttpClient) {}

    create(departmentHead: IDepartmentHead): Observable<EntityResponseType> {
        return this.http.post<IDepartmentHead>(this.resourceUrl, departmentHead, { observe: 'response' });
    }

    update(departmentHead: IDepartmentHead): Observable<EntityResponseType> {
        return this.http.put<IDepartmentHead>(this.resourceUrl, departmentHead, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDepartmentHead>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDepartmentHead[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDepartmentHead[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
