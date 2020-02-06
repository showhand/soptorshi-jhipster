import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAttendance } from 'app/shared/model/attendance.model';

type EntityResponseType = HttpResponse<IAttendance>;
type EntityArrayResponseType = HttpResponse<IAttendance[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceService {
    public resourceUrl = SERVER_API_URL + 'api/attendances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/attendances';

    constructor(protected http: HttpClient) {}

    create(attendance: IAttendance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(attendance);
        return this.http
            .post<IAttendance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(attendance: IAttendance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(attendance);
        return this.http
            .put<IAttendance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAttendance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAttendance[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAttendance[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(attendance: IAttendance): IAttendance {
        const copy: IAttendance = Object.assign({}, attendance, {
            attendanceDate:
                attendance.attendanceDate != null && attendance.attendanceDate.isValid()
                    ? attendance.attendanceDate.format(DATE_FORMAT)
                    : null,
            inTime: attendance.inTime != null && attendance.inTime.isValid() ? attendance.inTime.toJSON() : null,
            outTime: attendance.outTime != null && attendance.outTime.isValid() ? attendance.outTime.toJSON() : null,
            createdOn: attendance.createdOn != null && attendance.createdOn.isValid() ? attendance.createdOn.toJSON() : null,
            updatedOn: attendance.updatedOn != null && attendance.updatedOn.isValid() ? attendance.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.attendanceDate = res.body.attendanceDate != null ? moment(res.body.attendanceDate) : null;
            res.body.inTime = res.body.inTime != null ? moment(res.body.inTime) : null;
            res.body.outTime = res.body.outTime != null ? moment(res.body.outTime) : null;
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((attendance: IAttendance) => {
                attendance.attendanceDate = attendance.attendanceDate != null ? moment(attendance.attendanceDate) : null;
                attendance.inTime = attendance.inTime != null ? moment(attendance.inTime) : null;
                attendance.outTime = attendance.outTime != null ? moment(attendance.outTime) : null;
                attendance.createdOn = attendance.createdOn != null ? moment(attendance.createdOn) : null;
                attendance.updatedOn = attendance.updatedOn != null ? moment(attendance.updatedOn) : null;
            });
        }
        return res;
    }
}
