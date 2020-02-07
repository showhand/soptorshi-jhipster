import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';
import { PaymentVoucherService } from 'app/entities/payment-voucher';

type EntityResponseType = HttpResponse<IPaymentVoucher>;
type EntityArrayResponseType = HttpResponse<IPaymentVoucher[]>;

@Injectable({ providedIn: 'root' })
export class PaymentVoucherExtendedService extends PaymentVoucherService {
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/payment-vouchers';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(paymentVoucher: IPaymentVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(paymentVoucher);
        return this.http
            .post<IPaymentVoucher>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(paymentVoucher: IPaymentVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(paymentVoucher);
        return this.http
            .put<IPaymentVoucher>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findByVoucherNo(voucherNo: string): Observable<EntityResponseType> {
        return this.http
            .get<IPaymentVoucher>(`${this.resourceUrlExtended}/voucherNo/${voucherNo}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
