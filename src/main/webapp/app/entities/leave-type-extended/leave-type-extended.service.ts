import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type';

type EntityResponseType = HttpResponse<ILeaveType>;
type EntityArrayResponseType = HttpResponse<ILeaveType[]>;

@Injectable({ providedIn: 'root' })
export class LeaveTypeExtendedService extends LeaveTypeService {
    public resourceUrl = SERVER_API_URL + 'api/leave-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leave-types';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(leaveType: ILeaveType): Observable<EntityResponseType> {
        return this.http.post<ILeaveType>(this.resourceUrl, leaveType, { observe: 'response' });
    }

    update(leaveType: ILeaveType): Observable<EntityResponseType> {
        return this.http.put<ILeaveType>(this.resourceUrl, leaveType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILeaveType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILeaveType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILeaveType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
