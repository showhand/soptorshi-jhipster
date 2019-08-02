import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockOutItem } from 'app/shared/model/stock-out-item.model';

type EntityResponseType = HttpResponse<IStockOutItem>;
type EntityArrayResponseType = HttpResponse<IStockOutItem[]>;

@Injectable({ providedIn: 'root' })
export class StockOutItemService {
    public resourceUrl = SERVER_API_URL + 'api/stock-out-items';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/stock-out-items';

    constructor(protected http: HttpClient) {}

    create(stockOutItem: IStockOutItem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockOutItem);
        return this.http
            .post<IStockOutItem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(stockOutItem: IStockOutItem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockOutItem);
        return this.http
            .put<IStockOutItem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStockOutItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStockOutItem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStockOutItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(stockOutItem: IStockOutItem): IStockOutItem {
        const copy: IStockOutItem = Object.assign({}, stockOutItem, {
            stockOutDate:
                stockOutItem.stockOutDate != null && stockOutItem.stockOutDate.isValid() ? stockOutItem.stockOutDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.stockOutDate = res.body.stockOutDate != null ? moment(res.body.stockOutDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((stockOutItem: IStockOutItem) => {
                stockOutItem.stockOutDate = stockOutItem.stockOutDate != null ? moment(stockOutItem.stockOutDate) : null;
            });
        }
        return res;
    }
}
