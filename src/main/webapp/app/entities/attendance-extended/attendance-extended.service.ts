import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { IAttendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from 'app/entities/attendance';

type EntityResponseType = HttpResponse<IAttendance>;
type EntityArrayResponseType = HttpResponse<IAttendance[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceExtendedService extends AttendanceService {
    public resourceUrl = SERVER_API_URL + 'api/extended/attendances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/attendances';

    constructor(protected http: HttpClient) {
        super(http);
    }

    getDistinctAttendanceDate(): Observable<EntityArrayResponseType> {
        return this.http
            .get<IAttendance[]>(`${this.resourceUrl}/distinct`, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }
}
