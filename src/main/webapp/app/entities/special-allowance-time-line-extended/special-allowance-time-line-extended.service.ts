import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';
import { SpecialAllowanceTimeLineService } from 'app/entities/special-allowance-time-line';

type EntityResponseType = HttpResponse<ISpecialAllowanceTimeLine>;
type EntityArrayResponseType = HttpResponse<ISpecialAllowanceTimeLine[]>;

@Injectable({ providedIn: 'root' })
export class SpecialAllowanceTimeLineExtendedService extends SpecialAllowanceTimeLineService {
    constructor(protected http: HttpClient) {
        super(http);
    }
}
