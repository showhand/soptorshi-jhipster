import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IWeekend } from 'app/shared/model/weekend.model';
import { WeekendService } from 'app/entities/weekend';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<IWeekend>;
type EntityArrayResponseType = HttpResponse<IWeekend[]>;

@Injectable({ providedIn: 'root' })
export class WeekendExtendedService extends WeekendService {
    public resourceUrl = SERVER_API_URL + 'api/extended/weekends';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/weekends';

    constructor(protected http: HttpClient) {
        super(http);
    }

    generateReport() {
        return this.http
            .get(`${this.resourceUrl}/report/all`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Weekends');
            });
    }
}
