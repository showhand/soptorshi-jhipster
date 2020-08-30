import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMonthlySalary, MonthType } from 'app/shared/model/monthly-salary.model';
import { MonthlySalaryService } from 'app/entities/monthly-salary';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<IMonthlySalary>;
type EntityArrayResponseType = HttpResponse<IMonthlySalary[]>;

@Injectable({ providedIn: 'root' })
export class MonthlySalaryExtendedService extends MonthlySalaryService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/monthly-salaries';

    constructor(protected http: HttpClient) {
        super(http);
    }

    createSalaryVouchers(officeId: number, year: number, monthType: MonthType): Observable<EntityResponseType> {
        return this.http
            .get<IMonthlySalary>(`${this.resourceUrlExtended}/${officeId}/${year}/${monthType}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => res));
    }

    createPayrollReport(officeId: number, year: number, monthType: MonthType): void {
        this.http
            .get(`${this.resourceUrlExtended}/salary-report/${officeId}/${year}/${monthType}`, { observe: 'response' })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Salary Report');
            });
    }

    getTotalWorkingDays(month: MonthType, year: number): Observable<HttpResponse<number>> {
        return this.http
            .get(SERVER_API_URL + 'api/extended/payroll-support/totalWorkDays/month/' + month + '/year/' + year, { observe: 'response' })
            .pipe(map((res: HttpResponse<number>) => res));
    }

    getTotalPresent(employeeId: number, month: MonthType, year: number): Observable<HttpResponse<number>> {
        return this.http
            .get(
                SERVER_API_URL + 'api/extended/payroll-support/totalPresent/employeeId/' + employeeId + '/month/' + month + '/year/' + year,
                { observe: 'response' }
            )
            .pipe(map((res: HttpResponse<number>) => res));
    }

    getTotalPresentWithoutPay(employeeId: number, month: MonthType, year: number): Observable<HttpResponse<number>> {
        return this.http
            .get(
                SERVER_API_URL +
                    'api/extended/payroll-support/totalPresentWithoutPay/employeeId/' +
                    employeeId +
                    '/month/' +
                    month +
                    '/year/' +
                    year,
                { observe: 'response' }
            )
            .pipe(map((res: HttpResponse<number>) => res));
    }
}
