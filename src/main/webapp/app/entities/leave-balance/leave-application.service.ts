import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';

type EntityResponseType = HttpResponse<ILeaveApplication>;
type EntityArrayResponseType = HttpResponse<ILeaveApplication[]>;

@Injectable({ providedIn: 'root' })
export class LeaveApplicationService {
    public resourceUrl = SERVER_API_URL + 'api/leave-applications';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leave-applications';

    constructor(protected http: HttpClient) {}

    create(leaveApplication: ILeaveApplication): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(leaveApplication);
        return this.http
            .post<ILeaveApplication>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(leaveApplication: ILeaveApplication): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(leaveApplication);
        return this.http
            .put<ILeaveApplication>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILeaveApplication>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILeaveApplication[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILeaveApplication[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(leaveApplication: ILeaveApplication): ILeaveApplication {
        const copy: ILeaveApplication = Object.assign({}, leaveApplication, {
            fromDate:
                leaveApplication.fromDate != null && leaveApplication.fromDate.isValid()
                    ? leaveApplication.fromDate.format(DATE_FORMAT)
                    : null,
            toDate:
                leaveApplication.toDate != null && leaveApplication.toDate.isValid() ? leaveApplication.toDate.format(DATE_FORMAT) : null,
            appliedOn:
                leaveApplication.appliedOn != null && leaveApplication.appliedOn.isValid() ? leaveApplication.appliedOn.toJSON() : null,
            actionTakenOn:
                leaveApplication.actionTakenOn != null && leaveApplication.actionTakenOn.isValid()
                    ? leaveApplication.actionTakenOn.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fromDate = res.body.fromDate != null ? moment(res.body.fromDate) : null;
            res.body.toDate = res.body.toDate != null ? moment(res.body.toDate) : null;
            res.body.appliedOn = res.body.appliedOn != null ? moment(res.body.appliedOn) : null;
            res.body.actionTakenOn = res.body.actionTakenOn != null ? moment(res.body.actionTakenOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((leaveApplication: ILeaveApplication) => {
                leaveApplication.fromDate = leaveApplication.fromDate != null ? moment(leaveApplication.fromDate) : null;
                leaveApplication.toDate = leaveApplication.toDate != null ? moment(leaveApplication.toDate) : null;
                leaveApplication.appliedOn = leaveApplication.appliedOn != null ? moment(leaveApplication.appliedOn) : null;
                leaveApplication.actionTakenOn = leaveApplication.actionTakenOn != null ? moment(leaveApplication.actionTakenOn) : null;
            });
        }
        return res;
    }
}
