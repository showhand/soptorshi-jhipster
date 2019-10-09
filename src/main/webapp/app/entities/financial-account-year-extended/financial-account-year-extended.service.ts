import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';
import { FinancialAccountYearService } from 'app/entities/financial-account-year';

type EntityResponseType = HttpResponse<IFinancialAccountYear>;
type EntityArrayResponseType = HttpResponse<IFinancialAccountYear[]>;

@Injectable({ providedIn: 'root' })
export class FinancialAccountYearExtendedService extends FinancialAccountYearService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/financial-account-years';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(financialAccountYear: IFinancialAccountYear): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(financialAccountYear);
        return this.http
            .post<IFinancialAccountYear>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(financialAccountYear: IFinancialAccountYear): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(financialAccountYear);
        return this.http
            .put<IFinancialAccountYear>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
