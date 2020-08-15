import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IOverTime } from 'app/shared/model/over-time.model';
import { OverTimeService } from 'app/entities/over-time';
import { Moment } from 'moment';
import { DATE_FORMAT } from 'app/shared';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<IOverTime>;
type EntityArrayResponseType = HttpResponse<IOverTime[]>;

@Injectable({ providedIn: 'root' })
export class OverTimeExtendedService extends OverTimeService {
    public resourceUrl = SERVER_API_URL + 'api/extended/over-times';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/over-times';

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
                SoptorshiUtil.writeFileContent(data, 'application/pdf', `OverTime`);
            });
    }

    generateReportByFromDateAndToDate(fromDate: Moment, toDate: Moment) {
        return this.http
            .get(`${this.resourceUrl}/report/fromDate/${fromDate.format(DATE_FORMAT)}/toDate/${toDate.format(DATE_FORMAT)}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', `OverTime`);
            });
    }

    generateReportByEmployeeId(employeeId: string) {
        return this.http
            .get(`${this.resourceUrl}/report/employeeId/${employeeId}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', `OverTime`);
            });
    }

    generateReportByFromDateAndToDateAndCurrentLoggedInUser(fromDate: Moment, toDate: Moment) {
        return this.http
            .get(`${this.resourceUrl}/report/fromDate/${fromDate.format(DATE_FORMAT)}/toDate/${toDate.format(DATE_FORMAT)}/loggedInUser`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', `OverTime`);
            });
    }
}
