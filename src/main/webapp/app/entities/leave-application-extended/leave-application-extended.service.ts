import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from 'app/entities/leave-application';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';
import { Moment } from 'moment';
import { DATE_FORMAT } from 'app/shared';

type EntityResponseType = HttpResponse<ILeaveApplication>;
type EntityArrayResponseType = HttpResponse<ILeaveApplication[]>;

@Injectable({ providedIn: 'root' })
export class LeaveApplicationExtendedService extends LeaveApplicationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/leave-applications';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leave-applications';

    constructor(protected http: HttpClient) {
        super(http);
    }

    generateReportByFromDateAndToDateAndEmployeeId(fromDate: Moment, toDate: Moment, employeeId: string) {
        return this.http
            .get(
                `${this.resourceUrl}/history/report/fromDate/${fromDate.format(DATE_FORMAT)}/toDate/${toDate.format(
                    DATE_FORMAT
                )}/employeeId/${employeeId}`,
                {
                    responseType: 'blob'
                }
            )
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Leave History');
            });
    }

    generateReportByFromDateAndToDate(fromDate: Moment, toDate: Moment) {
        return this.http
            .get(`${this.resourceUrl}/history/report/fromDate/${fromDate.format(DATE_FORMAT)}/toDate/${toDate.format(DATE_FORMAT)}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Leave History');
            });
    }

    calculateDifference(fromDate: string, toDate: string): Observable<HttpResponse<number>> {
        return this.http
            .get<number>(`${this.resourceUrl}/calculateDiff/fromDate/${fromDate}/toDate/${toDate}`, { observe: 'response' })
            .pipe(map((res: HttpResponse<number>) => res));
    }
}
