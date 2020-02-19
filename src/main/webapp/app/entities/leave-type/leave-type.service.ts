import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILeaveType } from 'app/shared/model/leave-type.model';

type EntityResponseType = HttpResponse<ILeaveType>;
type EntityArrayResponseType = HttpResponse<ILeaveType[]>;

@Injectable({ providedIn: 'root' })
export class LeaveTypeService {
    public resourceUrl = SERVER_API_URL + 'api/leave-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leave-types';

    constructor(protected http: HttpClient) {}

    create(leaveType: ILeaveType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(leaveType);
        return this.http
            .post<ILeaveType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(leaveType: ILeaveType): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(leaveType);
        return this.http
            .put<ILeaveType>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILeaveType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILeaveType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILeaveType[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(leaveType: ILeaveType): ILeaveType {
        const copy: ILeaveType = Object.assign({}, leaveType, {
            createdOn: leaveType.createdOn != null && leaveType.createdOn.isValid() ? leaveType.createdOn.toJSON() : null,
            updatedOn: leaveType.updatedOn != null && leaveType.updatedOn.isValid() ? leaveType.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((leaveType: ILeaveType) => {
                leaveType.createdOn = leaveType.createdOn != null ? moment(leaveType.createdOn) : null;
                leaveType.updatedOn = leaveType.updatedOn != null ? moment(leaveType.updatedOn) : null;
            });
        }
        return res;
    }
}
