import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPaymentVoucher } from 'app/shared/model/payment-voucher.model';

type EntityResponseType = HttpResponse<IPaymentVoucher>;
type EntityArrayResponseType = HttpResponse<IPaymentVoucher[]>;

@Injectable({ providedIn: 'root' })
export class PaymentVoucherService {
    public resourceUrl = SERVER_API_URL + 'api/payment-vouchers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/payment-vouchers';

    constructor(protected http: HttpClient) {}

    create(paymentVoucher: IPaymentVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(paymentVoucher);
        return this.http
            .post<IPaymentVoucher>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(paymentVoucher: IPaymentVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(paymentVoucher);
        return this.http
            .put<IPaymentVoucher>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPaymentVoucher>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPaymentVoucher[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPaymentVoucher[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(paymentVoucher: IPaymentVoucher): IPaymentVoucher {
        const copy: IPaymentVoucher = Object.assign({}, paymentVoucher, {
            voucherDate:
                paymentVoucher.voucherDate != null && paymentVoucher.voucherDate.isValid()
                    ? paymentVoucher.voucherDate.format(DATE_FORMAT)
                    : null,
            postDate:
                paymentVoucher.postDate != null && paymentVoucher.postDate.isValid() ? paymentVoucher.postDate.format(DATE_FORMAT) : null,
            modifiedOn:
                paymentVoucher.modifiedOn != null && paymentVoucher.modifiedOn.isValid()
                    ? paymentVoucher.modifiedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.voucherDate = res.body.voucherDate != null ? moment(res.body.voucherDate) : null;
            res.body.postDate = res.body.postDate != null ? moment(res.body.postDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((paymentVoucher: IPaymentVoucher) => {
                paymentVoucher.voucherDate = paymentVoucher.voucherDate != null ? moment(paymentVoucher.voucherDate) : null;
                paymentVoucher.postDate = paymentVoucher.postDate != null ? moment(paymentVoucher.postDate) : null;
                paymentVoucher.modifiedOn = paymentVoucher.modifiedOn != null ? moment(paymentVoucher.modifiedOn) : null;
            });
        }
        return res;
    }
}
