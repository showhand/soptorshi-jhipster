import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IContraVoucher } from 'app/shared/model/contra-voucher.model';

type EntityResponseType = HttpResponse<IContraVoucher>;
type EntityArrayResponseType = HttpResponse<IContraVoucher[]>;

@Injectable({ providedIn: 'root' })
export class ContraVoucherService {
    public resourceUrl = SERVER_API_URL + 'api/contra-vouchers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/contra-vouchers';

    constructor(protected http: HttpClient) {}

    create(contraVoucher: IContraVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contraVoucher);
        return this.http
            .post<IContraVoucher>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(contraVoucher: IContraVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(contraVoucher);
        return this.http
            .put<IContraVoucher>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IContraVoucher>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContraVoucher[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IContraVoucher[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(contraVoucher: IContraVoucher): IContraVoucher {
        const copy: IContraVoucher = Object.assign({}, contraVoucher, {
            voucherDate:
                contraVoucher.voucherDate != null && contraVoucher.voucherDate.isValid()
                    ? contraVoucher.voucherDate.format(DATE_FORMAT)
                    : null,
            postDate:
                contraVoucher.postDate != null && contraVoucher.postDate.isValid() ? contraVoucher.postDate.format(DATE_FORMAT) : null,
            modifiedOn:
                contraVoucher.modifiedOn != null && contraVoucher.modifiedOn.isValid() ? contraVoucher.modifiedOn.format(DATE_FORMAT) : null
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
            res.body.forEach((contraVoucher: IContraVoucher) => {
                contraVoucher.voucherDate = contraVoucher.voucherDate != null ? moment(contraVoucher.voucherDate) : null;
                contraVoucher.postDate = contraVoucher.postDate != null ? moment(contraVoucher.postDate) : null;
                contraVoucher.modifiedOn = contraVoucher.modifiedOn != null ? moment(contraVoucher.modifiedOn) : null;
            });
        }
        return res;
    }
}
