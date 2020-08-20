import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from 'app/entities/holiday-type';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<IHolidayType>;
type EntityArrayResponseType = HttpResponse<IHolidayType[]>;

@Injectable({ providedIn: 'root' })
export class HolidayTypeExtendedService extends HolidayTypeService {
    public resourceUrl = SERVER_API_URL + 'api/extended/holiday-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/holiday-types';

    constructor(protected http: HttpClient) {
        super(http);
    }

    generateReport() {
        return this.http
            .get(`${this.resourceUrl}/report/all`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Holiday Types');
            });
    }
}
