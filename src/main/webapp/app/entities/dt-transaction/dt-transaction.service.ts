import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDtTransaction } from 'app/shared/model/dt-transaction.model';

type EntityResponseType = HttpResponse<IDtTransaction>;
type EntityArrayResponseType = HttpResponse<IDtTransaction[]>;

@Injectable({ providedIn: 'root' })
export class DtTransactionService {
    public resourceUrl = SERVER_API_URL + 'api/dt-transactions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/dt-transactions';

    constructor(protected http: HttpClient) {}

    create(dtTransaction: IDtTransaction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dtTransaction);
        return this.http
            .post<IDtTransaction>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(dtTransaction: IDtTransaction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(dtTransaction);
        return this.http
            .put<IDtTransaction>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDtTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDtTransaction[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDtTransaction[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(dtTransaction: IDtTransaction): IDtTransaction {
        const copy: IDtTransaction = Object.assign({}, dtTransaction, {
            voucherDate:
                dtTransaction.voucherDate != null && dtTransaction.voucherDate.isValid()
                    ? dtTransaction.voucherDate.format(DATE_FORMAT)
                    : null,
            invoiceDate:
                dtTransaction.invoiceDate != null && dtTransaction.invoiceDate.isValid()
                    ? dtTransaction.invoiceDate.format(DATE_FORMAT)
                    : null,
            instrumentDate:
                dtTransaction.instrumentDate != null && dtTransaction.instrumentDate.isValid()
                    ? dtTransaction.instrumentDate.format(DATE_FORMAT)
                    : null,
            postDate:
                dtTransaction.postDate != null && dtTransaction.postDate.isValid() ? dtTransaction.postDate.format(DATE_FORMAT) : null,
            modifiedOn:
                dtTransaction.modifiedOn != null && dtTransaction.modifiedOn.isValid() ? dtTransaction.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.voucherDate = res.body.voucherDate != null ? moment(res.body.voucherDate) : null;
            res.body.invoiceDate = res.body.invoiceDate != null ? moment(res.body.invoiceDate) : null;
            res.body.instrumentDate = res.body.instrumentDate != null ? moment(res.body.instrumentDate) : null;
            res.body.postDate = res.body.postDate != null ? moment(res.body.postDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((dtTransaction: IDtTransaction) => {
                dtTransaction.voucherDate = dtTransaction.voucherDate != null ? moment(dtTransaction.voucherDate) : null;
                dtTransaction.invoiceDate = dtTransaction.invoiceDate != null ? moment(dtTransaction.invoiceDate) : null;
                dtTransaction.instrumentDate = dtTransaction.instrumentDate != null ? moment(dtTransaction.instrumentDate) : null;
                dtTransaction.postDate = dtTransaction.postDate != null ? moment(dtTransaction.postDate) : null;
                dtTransaction.modifiedOn = dtTransaction.modifiedOn != null ? moment(dtTransaction.modifiedOn) : null;
            });
        }
        return res;
    }
}
