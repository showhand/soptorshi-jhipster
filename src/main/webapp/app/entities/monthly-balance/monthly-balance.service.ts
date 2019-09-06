import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMonthlyBalance } from 'app/shared/model/monthly-balance.model';

type EntityResponseType = HttpResponse<IMonthlyBalance>;
type EntityArrayResponseType = HttpResponse<IMonthlyBalance[]>;

@Injectable({ providedIn: 'root' })
export class MonthlyBalanceService {
    public resourceUrl = SERVER_API_URL + 'api/monthly-balances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/monthly-balances';

    constructor(protected http: HttpClient) {}

    create(monthlyBalance: IMonthlyBalance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(monthlyBalance);
        return this.http
            .post<IMonthlyBalance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(monthlyBalance: IMonthlyBalance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(monthlyBalance);
        return this.http
            .put<IMonthlyBalance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMonthlyBalance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMonthlyBalance[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMonthlyBalance[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(monthlyBalance: IMonthlyBalance): IMonthlyBalance {
        const copy: IMonthlyBalance = Object.assign({}, monthlyBalance, {
            modifiedOn:
                monthlyBalance.modifiedOn != null && monthlyBalance.modifiedOn.isValid()
                    ? monthlyBalance.modifiedOn.format(DATE_FORMAT)
                    : null
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
            res.body.forEach((monthlyBalance: IMonthlyBalance) => {
                monthlyBalance.modifiedOn = monthlyBalance.modifiedOn != null ? moment(monthlyBalance.modifiedOn) : null;
            });
        }
        return res;
    }
}
