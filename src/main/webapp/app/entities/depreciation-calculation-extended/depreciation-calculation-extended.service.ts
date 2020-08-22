import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';
import { DepreciationCalculationService } from 'app/entities/depreciation-calculation';

type EntityResponseType = HttpResponse<IDepreciationCalculation>;
type EntityArrayResponseType = HttpResponse<IDepreciationCalculation[]>;

@Injectable({ providedIn: 'root' })
export class DepreciationCalculationExtendedService extends DepreciationCalculationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/depreciation-calculations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/depreciation-calculations';

    constructor(protected http: HttpClient) {
        super(http);
    }
}
