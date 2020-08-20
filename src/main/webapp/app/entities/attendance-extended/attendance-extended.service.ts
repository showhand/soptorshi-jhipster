import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IAttendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from 'app/entities/attendance';
import { Moment } from 'moment';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';
import { DATE_FORMAT } from 'app/shared';

type EntityResponseType = HttpResponse<IAttendance>;
type EntityArrayResponseType = HttpResponse<IAttendance[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceExtendedService extends AttendanceService {
    public resourceUrl = SERVER_API_URL + 'api/extended/attendances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/attendances';

    constructor(protected http: HttpClient) {
        super(http);
    }

    generateReportByFromDateAndToDateAndEmployeeId(fromDate: Moment, toDate: Moment, employeeId: string) {
        return this.http
            .get(
                `${this.resourceUrl}/report/fromDate/${fromDate.format(DATE_FORMAT)}/toDate/${toDate.format(
                    DATE_FORMAT
                )}/employeeId/${employeeId}`,
                {
                    responseType: 'blob'
                }
            )
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', `Attendances`);
            });
    }

    generateReportByFromDateAndToDate(fromDate: Moment, toDate: Moment) {
        return this.http
            .get(`${this.resourceUrl}/report/fromDate/${fromDate.format(DATE_FORMAT)}/toDate/${toDate.format(DATE_FORMAT)}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', `Attendances`);
            });
    }

    generateReportByEmployeeId(employeeId: string) {
        return this.http
            .get(`${this.resourceUrl}/report/employeeId/${employeeId}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', `Attendances`);
            });
    }

    generateReportByFromDateAndToDateAndCurrentLoggedInUser(fromDate: Moment, toDate: Moment) {
        return this.http
            .get(`${this.resourceUrl}/report/fromDate/${fromDate.format(DATE_FORMAT)}/toDate/${toDate.format(DATE_FORMAT)}/loggedInUser`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', `Attendances`);
            });
    }
}
