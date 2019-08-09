import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockStatus } from 'app/shared/model/stock-status.model';

type EntityResponseType = HttpResponse<IStockStatus>;
type EntityArrayResponseType = HttpResponse<IStockStatus[]>;

@Injectable({ providedIn: 'root' })
export class StockStatusService {
    public resourceUrl = SERVER_API_URL + 'api/stock-statuses';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/stock-statuses';

    constructor(protected http: HttpClient) {}

    create(stockStatus: IStockStatus): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockStatus);
        return this.http
            .post<IStockStatus>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(stockStatus: IStockStatus): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockStatus);
        return this.http
            .put<IStockStatus>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStockStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStockStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStockStatus[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(stockStatus: IStockStatus): IStockStatus {
        const copy: IStockStatus = Object.assign({}, stockStatus, {
            stockInDate: stockStatus.stockInDate != null && stockStatus.stockInDate.isValid() ? stockStatus.stockInDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.stockInDate = res.body.stockInDate != null ? moment(res.body.stockInDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((stockStatus: IStockStatus) => {
                stockStatus.stockInDate = stockStatus.stockInDate != null ? moment(stockStatus.stockInDate) : null;
            });
        }
        return res;
    }
}
