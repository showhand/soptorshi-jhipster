import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJournalVoucher } from 'app/shared/model/journal-voucher.model';

type EntityResponseType = HttpResponse<IJournalVoucher>;
type EntityArrayResponseType = HttpResponse<IJournalVoucher[]>;

@Injectable({ providedIn: 'root' })
export class JournalVoucherService {
    public resourceUrl = SERVER_API_URL + 'api/journal-vouchers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/journal-vouchers';

    constructor(protected http: HttpClient) {}

    create(journalVoucher: IJournalVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(journalVoucher);
        return this.http
            .post<IJournalVoucher>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(journalVoucher: IJournalVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(journalVoucher);
        return this.http
            .put<IJournalVoucher>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IJournalVoucher>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IJournalVoucher[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IJournalVoucher[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(journalVoucher: IJournalVoucher): IJournalVoucher {
        const copy: IJournalVoucher = Object.assign({}, journalVoucher, {
            voucherDate:
                journalVoucher.voucherDate != null && journalVoucher.voucherDate.isValid()
                    ? journalVoucher.voucherDate.format(DATE_FORMAT)
                    : null,
            postDate:
                journalVoucher.postDate != null && journalVoucher.postDate.isValid() ? journalVoucher.postDate.format(DATE_FORMAT) : null,
            modifiedOn:
                journalVoucher.modifiedOn != null && journalVoucher.modifiedOn.isValid()
                    ? journalVoucher.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((journalVoucher: IJournalVoucher) => {
                journalVoucher.voucherDate = journalVoucher.voucherDate != null ? moment(journalVoucher.voucherDate) : null;
                journalVoucher.postDate = journalVoucher.postDate != null ? moment(journalVoucher.postDate) : null;
                journalVoucher.modifiedOn = journalVoucher.modifiedOn != null ? moment(journalVoucher.modifiedOn) : null;
            });
        }
        return res;
    }
}
