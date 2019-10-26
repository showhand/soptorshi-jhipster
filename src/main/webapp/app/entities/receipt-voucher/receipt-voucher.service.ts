import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReceiptVoucher } from 'app/shared/model/receipt-voucher.model';

type EntityResponseType = HttpResponse<IReceiptVoucher>;
type EntityArrayResponseType = HttpResponse<IReceiptVoucher[]>;

@Injectable({ providedIn: 'root' })
export class ReceiptVoucherService {
    public resourceUrl = SERVER_API_URL + 'api/receipt-vouchers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/receipt-vouchers';

    constructor(protected http: HttpClient) {}

    create(receiptVoucher: IReceiptVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(receiptVoucher);
        return this.http
            .post<IReceiptVoucher>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(receiptVoucher: IReceiptVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(receiptVoucher);
        return this.http
            .put<IReceiptVoucher>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IReceiptVoucher>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReceiptVoucher[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReceiptVoucher[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(receiptVoucher: IReceiptVoucher): IReceiptVoucher {
        const copy: IReceiptVoucher = Object.assign({}, receiptVoucher, {
            voucherDate:
                receiptVoucher.voucherDate != null && receiptVoucher.voucherDate.isValid()
                    ? receiptVoucher.voucherDate.format(DATE_FORMAT)
                    : null,
            postDate:
                receiptVoucher.postDate != null && receiptVoucher.postDate.isValid() ? receiptVoucher.postDate.format(DATE_FORMAT) : null,
            modifiedOn:
                receiptVoucher.modifiedOn != null && receiptVoucher.modifiedOn.isValid()
                    ? receiptVoucher.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((receiptVoucher: IReceiptVoucher) => {
                receiptVoucher.voucherDate = receiptVoucher.voucherDate != null ? moment(receiptVoucher.voucherDate) : null;
                receiptVoucher.postDate = receiptVoucher.postDate != null ? moment(receiptVoucher.postDate) : null;
                receiptVoucher.modifiedOn = receiptVoucher.modifiedOn != null ? moment(receiptVoucher.modifiedOn) : null;
            });
        }
        return res;
    }
}
