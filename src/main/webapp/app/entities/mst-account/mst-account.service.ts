import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMstAccount } from 'app/shared/model/mst-account.model';

type EntityResponseType = HttpResponse<IMstAccount>;
type EntityArrayResponseType = HttpResponse<IMstAccount[]>;

@Injectable({ providedIn: 'root' })
export class MstAccountService {
    public resourceUrl = SERVER_API_URL + 'api/mst-accounts';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/mst-accounts';

    constructor(protected http: HttpClient) {}

    create(mstAccount: IMstAccount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(mstAccount);
        return this.http
            .post<IMstAccount>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(mstAccount: IMstAccount): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(mstAccount);
        return this.http
            .put<IMstAccount>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMstAccount>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMstAccount[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMstAccount[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(mstAccount: IMstAccount): IMstAccount {
        const copy: IMstAccount = Object.assign({}, mstAccount, {
            modifiedOn: mstAccount.modifiedOn != null && mstAccount.modifiedOn.isValid() ? mstAccount.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((mstAccount: IMstAccount) => {
                mstAccount.modifiedOn = mstAccount.modifiedOn != null ? moment(mstAccount.modifiedOn) : null;
            });
        }
        return res;
    }
}
