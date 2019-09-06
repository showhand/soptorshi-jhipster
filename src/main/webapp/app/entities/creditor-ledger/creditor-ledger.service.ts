import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICreditorLedger } from 'app/shared/model/creditor-ledger.model';

type EntityResponseType = HttpResponse<ICreditorLedger>;
type EntityArrayResponseType = HttpResponse<ICreditorLedger[]>;

@Injectable({ providedIn: 'root' })
export class CreditorLedgerService {
    public resourceUrl = SERVER_API_URL + 'api/creditor-ledgers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/creditor-ledgers';

    constructor(protected http: HttpClient) {}

    create(creditorLedger: ICreditorLedger): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(creditorLedger);
        return this.http
            .post<ICreditorLedger>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(creditorLedger: ICreditorLedger): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(creditorLedger);
        return this.http
            .put<ICreditorLedger>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICreditorLedger>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICreditorLedger[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICreditorLedger[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(creditorLedger: ICreditorLedger): ICreditorLedger {
        const copy: ICreditorLedger = Object.assign({}, creditorLedger, {
            billDate:
                creditorLedger.billDate != null && creditorLedger.billDate.isValid() ? creditorLedger.billDate.format(DATE_FORMAT) : null,
            dueDate: creditorLedger.dueDate != null && creditorLedger.dueDate.isValid() ? creditorLedger.dueDate.format(DATE_FORMAT) : null,
            modifiedOn:
                creditorLedger.modifiedOn != null && creditorLedger.modifiedOn.isValid()
                    ? creditorLedger.modifiedOn.format(DATE_FORMAT)
                    : null
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
            res.body.forEach((creditorLedger: ICreditorLedger) => {
                creditorLedger.billDate = creditorLedger.billDate != null ? moment(creditorLedger.billDate) : null;
                creditorLedger.dueDate = creditorLedger.dueDate != null ? moment(creditorLedger.dueDate) : null;
                creditorLedger.modifiedOn = creditorLedger.modifiedOn != null ? moment(creditorLedger.modifiedOn) : null;
            });
        }
        return res;
    }
}
