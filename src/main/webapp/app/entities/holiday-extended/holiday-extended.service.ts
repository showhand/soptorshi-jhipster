import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IHoliday } from 'app/shared/model/holiday.model';
import { HolidayService } from 'app/entities/holiday';

type EntityResponseType = HttpResponse<IHoliday>;
type EntityArrayResponseType = HttpResponse<IHoliday[]>;

@Injectable({ providedIn: 'root' })
export class HolidayExtendedService extends HolidayService {
    public resourceUrl = SERVER_API_URL + 'api/extended/holidays';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/holidays';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
