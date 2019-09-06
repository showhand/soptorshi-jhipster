import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDebtorLedger } from 'app/shared/model/debtor-ledger.model';

type EntityResponseType = HttpResponse<IDebtorLedger>;
type EntityArrayResponseType = HttpResponse<IDebtorLedger[]>;

@Injectable({ providedIn: 'root' })
export class DebtorLedgerService {
    public resourceUrl = SERVER_API_URL + 'api/debtor-ledgers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/debtor-ledgers';

    constructor(protected http: HttpClient) {}

    create(debtorLedger: IDebtorLedger): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(debtorLedger);
        return this.http
            .post<IDebtorLedger>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(debtorLedger: IDebtorLedger): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(debtorLedger);
        return this.http
            .put<IDebtorLedger>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDebtorLedger>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDebtorLedger[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDebtorLedger[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(debtorLedger: IDebtorLedger): IDebtorLedger {
        const copy: IDebtorLedger = Object.assign({}, debtorLedger, {
            billDate: debtorLedger.billDate != null && debtorLedger.billDate.isValid() ? debtorLedger.billDate.format(DATE_FORMAT) : null,
            dueDate: debtorLedger.dueDate != null && debtorLedger.dueDate.isValid() ? debtorLedger.dueDate.format(DATE_FORMAT) : null,
            modifiedOn:
                debtorLedger.modifiedOn != null && debtorLedger.modifiedOn.isValid() ? debtorLedger.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.billDate = res.body.billDate != null ? moment(res.body.billDate) : null;
            res.body.dueDate = res.body.dueDate != null ? moment(res.body.dueDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((debtorLedger: IDebtorLedger) => {
                debtorLedger.billDate = debtorLedger.billDate != null ? moment(debtorLedger.billDate) : null;
                debtorLedger.dueDate = debtorLedger.dueDate != null ? moment(debtorLedger.dueDate) : null;
                debtorLedger.modifiedOn = debtorLedger.modifiedOn != null ? moment(debtorLedger.modifiedOn) : null;
            });
        }
        return res;
    }
}
