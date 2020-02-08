import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { IAttendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from 'app/entities/attendance';
import { Moment } from 'moment';

type EntityResponseType = HttpResponse<IAttendance>;
type EntityArrayResponseType = HttpResponse<IAttendance[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceExtendedService extends AttendanceService {
    public resourceUrl = SERVER_API_URL + 'api/extended/attendances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/attendances';

    constructor(protected http: HttpClient) {
        super(http);
    }

    getDistinctAttendanceDate(): Observable<HttpResponse<Moment[]>> {
        return this.http
            .get<Moment[]>(`${this.resourceUrl}/dates`, { observe: 'response' })
            .pipe(map((res: HttpResponse<Moment[]>) => this.convertDateArrayFromServerResponse(res)));
    }

    protected convertDateArrayFromServerResponse(res: HttpResponse<Moment[]>): HttpResponse<Moment[]> {
        if (res.body) {
            res.body.forEach((m: Moment) => {
                m = m != null ? m : null;
            });
        }
        return res;
    }
}
