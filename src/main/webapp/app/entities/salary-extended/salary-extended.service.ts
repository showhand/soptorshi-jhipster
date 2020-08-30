import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalary } from 'app/shared/model/salary.model';
import { MonthType } from 'app/shared/model/monthly-salary.model';
import { SalaryService } from 'app/entities/salary';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<ISalary>;
type EntityArrayResponseType = HttpResponse<ISalary[]>;

@Injectable({ providedIn: 'root' })
export class SalaryExtendedService extends SalaryService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/salaries';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/salaries';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(salary: ISalary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salary);
        return this.http
            .post<ISalary>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(salary: ISalary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salary);
        return this.http
            .put<ISalary>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    generatePayroll(officeId: number, designationId: number, year: number, monthType: MonthType): Observable<EntityResponseType> {
        designationId = designationId === undefined ? 9999 : designationId;
        return this.http
            .get<any>(`${this.resourceUrlExtended}/generatePayRoll-all/${officeId}/${designationId}/${year}/${monthType.toString()}`, {
                observe: 'response'
            })
            .pipe(map((res: any) => res));
    }

    createPayrollReport(officeId: number, year: number, monthType: MonthType) {
        return this.http
            .get(`${this.resourceUrlExtended}/salary-report/${officeId}/${year}/${monthType}`, { responseType: 'blob' })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Salary Report');
            });
    }

    generatePayrollEmployeeSpecific(
        officeId: number,
        year: number,
        monthType: MonthType,
        employeeId: number
    ): Observable<EntityResponseType> {
        return this.http
            .get<any>(`${this.resourceUrlExtended}/generatePayRoll-employee/${officeId}/${year}/${monthType.toString()}/${employeeId}`, {
                observe: 'response'
            })
            .pipe(map((res: any) => res));
    }

    approveAll(officeId: number, designationId: number, year: number, month: MonthType): Observable<EntityResponseType> {
        designationId = designationId === undefined ? 9999 : designationId;
        return this.http.put(
            `${this.resourceUrlExtended}/approveAll/office/${officeId}/designation/${designationId}/year/${year}/month/${month}`,
            {},
            { observe: 'response' }
        );
    }

    rejectAll(officeId: number, designationId: number, year: number, month: MonthType): Observable<EntityResponseType> {
        designationId = designationId === undefined ? 9999 : designationId;
        return this.http.put(
            `${this.resourceUrlExtended}/rejectAll/office/${officeId}/designation/${designationId}/year/${year}/month/${month}`,
            {},
            { observe: 'response' }
        );
    }
}
