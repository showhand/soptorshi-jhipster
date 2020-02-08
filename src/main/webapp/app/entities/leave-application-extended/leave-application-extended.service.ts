import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from 'app/entities/leave-application';

type EntityResponseType = HttpResponse<ILeaveApplication>;
type EntityArrayResponseType = HttpResponse<ILeaveApplication[]>;

@Injectable({ providedIn: 'root' })
export class LeaveApplicationExtendedService extends LeaveApplicationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/leave-applications';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leave-applications';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
