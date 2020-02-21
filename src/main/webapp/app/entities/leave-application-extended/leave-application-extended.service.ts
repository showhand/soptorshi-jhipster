import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from 'app/entities/leave-application';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

type EntityResponseType = HttpResponse<ILeaveApplication>;
type EntityArrayResponseType = HttpResponse<ILeaveApplication[]>;

@Injectable({ providedIn: 'root' })
export class LeaveApplicationExtendedService extends LeaveApplicationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/leave-applications';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leave-applications';

    constructor(protected http: HttpClient) {
        super(http);
    }

    calculateDifference(fromDate: string, toDate: string): Observable<HttpResponse<number>> {
        return this.http
            .get<number>(`${this.resourceUrl}/calculateDiff/fromDate/${fromDate}/toDate/${toDate}`, { observe: 'response' })
            .pipe(map((res: HttpResponse<number>) => res));
    }
}
