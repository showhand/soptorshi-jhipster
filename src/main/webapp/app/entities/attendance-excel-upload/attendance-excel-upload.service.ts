import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';

type EntityResponseType = HttpResponse<IAttendanceExcelUpload>;
type EntityArrayResponseType = HttpResponse<IAttendanceExcelUpload[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceExcelUploadService {
    public resourceUrl = SERVER_API_URL + 'api/attendance-excel-uploads';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/attendance-excel-uploads';

    constructor(protected http: HttpClient) {}

    create(attendanceExcelUpload: IAttendanceExcelUpload): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(attendanceExcelUpload);
        return this.http
            .post<IAttendanceExcelUpload>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(attendanceExcelUpload: IAttendanceExcelUpload): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(attendanceExcelUpload);
        return this.http
            .put<IAttendanceExcelUpload>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAttendanceExcelUpload>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAttendanceExcelUpload[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAttendanceExcelUpload[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(attendanceExcelUpload: IAttendanceExcelUpload): IAttendanceExcelUpload {
        const copy: IAttendanceExcelUpload = Object.assign({}, attendanceExcelUpload, {
            createdOn:
                attendanceExcelUpload.createdOn != null && attendanceExcelUpload.createdOn.isValid()
                    ? attendanceExcelUpload.createdOn.toJSON()
                    : null,
            updatedOn:
                attendanceExcelUpload.updatedOn != null && attendanceExcelUpload.updatedOn.isValid()
                    ? attendanceExcelUpload.updatedOn.toJSON()
                    : null
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
            res.body.forEach((attendanceExcelUpload: IAttendanceExcelUpload) => {
                attendanceExcelUpload.createdOn = attendanceExcelUpload.createdOn != null ? moment(attendanceExcelUpload.createdOn) : null;
                attendanceExcelUpload.updatedOn = attendanceExcelUpload.updatedOn != null ? moment(attendanceExcelUpload.updatedOn) : null;
            });
        }
        return res;
    }
}
