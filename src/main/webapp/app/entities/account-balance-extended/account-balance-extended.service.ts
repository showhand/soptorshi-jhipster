import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountBalance } from 'app/shared/model/account-balance.model';
import { AccountBalanceService } from 'app/entities/account-balance';
import { BalanceSheetFetchType } from 'app/entities/account-balance-extended/balance-sheet.component';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<IAccountBalance>;
type EntityArrayResponseType = HttpResponse<IAccountBalance[]>;

@Injectable({ providedIn: 'root' })
export class AccountBalanceExtendedService extends AccountBalanceService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/account-balances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/account-balances';

    constructor(protected http: HttpClient) {
        super(http);
    }

    downloadBalanceSheet(asOnDate: any) {
        const asOnDateStr = moment(asOnDate).format('YYYY-MM-DD');
        return this.http
            .get(`${this.resourceUrlExtended}/balance-sheet/${asOnDateStr}`, { responseType: 'blob' })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/vnd.ms-excel', 'BalanceSheet');
            });
    }
}
