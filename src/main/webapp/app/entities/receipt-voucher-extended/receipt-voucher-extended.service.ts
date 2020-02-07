import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';
import { ReceiptVoucherService } from 'app/entities/receipt-voucher';

type EntityResponseType = HttpResponse<IReceiptVoucher>;
type EntityArrayResponseType = HttpResponse<IReceiptVoucher[]>;

@Injectable({ providedIn: 'root' })
export class ReceiptVoucherExtendedService extends ReceiptVoucherService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/receipt-vouchers';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(receiptVoucher: IReceiptVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(receiptVoucher);
        return this.http
            .post<IReceiptVoucher>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(receiptVoucher: IReceiptVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(receiptVoucher);
        return this.http
            .put<IReceiptVoucher>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findByVoucherNo(voucherNo: string): Observable<EntityResponseType> {
        return this.http
            .get<IReceiptVoucher>(`${this.resourceUrl}/voucherNo/${voucherNo}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
