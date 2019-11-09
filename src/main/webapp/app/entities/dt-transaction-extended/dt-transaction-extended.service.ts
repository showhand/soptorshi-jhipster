import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDtTransaction } from 'app/shared/model/dt-transaction.model';
import { DtTransactionService } from 'app/entities/dt-transaction';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<IDtTransaction>;
type EntityArrayResponseType = HttpResponse<IDtTransaction[]>;

export enum GeneralLedgerFetchType {
    ALL = 'ALL',
    TRANSACTION_SPECIFIC = 'TRANSACTION_SPECIFIC',
    ACCOUNT_SPECIFIC = 'ACCOUNT_SPECIFIC'
}

@Injectable({ providedIn: 'root' })
export class DtTransactionExtendedService extends DtTransactionService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/dt-transactions';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(dtTransaction: IDtTransaction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dtTransaction);
        return this.http
            .post<IDtTransaction>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dtTransaction: IDtTransaction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dtTransaction);
        return this.http
            .put<IDtTransaction>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    downloadVoucherReport(voucherName: string, voucherNo: string, voucherDate: any) {
        const voucherDateStr = moment(voucherDate).format('YYYY-MM-DD');
        return this.http
            .get(`${this.resourceUrlExtended}/voucher-report/${voucherName}/${voucherNo}/${voucherDateStr}`, { responseType: 'blob' })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', voucherName);
            });
    }

    downloadGeneralLedgerReport(generalLedgerFetchType: GeneralLedgerFetchType, accountId: number, fromDate: any, toDate: any) {
        const fromDateStr = moment(fromDate).format('YYYY-MM-DD');
        const toDateStr = moment(toDate).format('YYYY-MM-DD');
        return this.http
            .get(`${this.resourceUrlExtended}/general-ledger-report/${generalLedgerFetchType}/${accountId}/${fromDateStr}/${toDateStr}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'General Ledger Report');
            });
    }
}
