import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
        return this.http.post<IAttendanceExcelUpload>(this.resourceUrl, attendanceExcelUpload, { observe: 'response' });
    }

    update(attendanceExcelUpload: IAttendanceExcelUpload): Observable<EntityResponseType> {
        return this.http.put<IAttendanceExcelUpload>(this.resourceUrl, attendanceExcelUpload, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAttendanceExcelUpload>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAttendanceExcelUpload[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAttendanceExcelUpload[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
