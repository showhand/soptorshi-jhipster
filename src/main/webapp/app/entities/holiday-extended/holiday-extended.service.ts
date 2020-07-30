import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayService } from 'app/entities/holiday';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<IHoliday>;
type EntityArrayResponseType = HttpResponse<IHoliday[]>;

@Injectable({ providedIn: 'root' })
export class HolidayExtendedService extends HolidayService {
    public resourceUrl = SERVER_API_URL + 'api/extended/holidays';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/holidays';

    constructor(protected http: HttpClient) {
        super(http);
    }

    generateReport() {
        return this.http
            .get(`${this.resourceUrl}/report/all`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Holidays');
            });
    }

    generateReportByYear(pYear: number) {
        return this.http
            .get(`${this.resourceUrl}/report/year/${pYear}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', `Holidays of ${pYear}`);
            });
    }
}
