import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockInItem } from 'app/shared/model/stock-in-item.model';

type EntityResponseType = HttpResponse<IStockInItem>;
type EntityArrayResponseType = HttpResponse<IStockInItem[]>;

@Injectable({ providedIn: 'root' })
export class StockInItemService {
    public resourceUrl = SERVER_API_URL + 'api/stock-in-items';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/stock-in-items';

    constructor(protected http: HttpClient) {}

    create(stockInItem: IStockInItem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockInItem);
        return this.http
            .post<IStockInItem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(stockInItem: IStockInItem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockInItem);
        return this.http
            .put<IStockInItem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStockInItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStockInItem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStockInItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(stockInItem: IStockInItem): IStockInItem {
        const copy: IStockInItem = Object.assign({}, stockInItem, {
            mfgDate: stockInItem.mfgDate != null && stockInItem.mfgDate.isValid() ? stockInItem.mfgDate.format(DATE_FORMAT) : null,
            expiryDate:
                stockInItem.expiryDate != null && stockInItem.expiryDate.isValid() ? stockInItem.expiryDate.format(DATE_FORMAT) : null,
            stockInDate: stockInItem.stockInDate != null && stockInItem.stockInDate.isValid() ? stockInItem.stockInDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.mfgDate = res.body.mfgDate != null ? moment(res.body.mfgDate) : null;
            res.body.expiryDate = res.body.expiryDate != null ? moment(res.body.expiryDate) : null;
            res.body.stockInDate = res.body.stockInDate != null ? moment(res.body.stockInDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((stockInItem: IStockInItem) => {
                stockInItem.mfgDate = stockInItem.mfgDate != null ? moment(stockInItem.mfgDate) : null;
                stockInItem.expiryDate = stockInItem.expiryDate != null ? moment(stockInItem.expiryDate) : null;
                stockInItem.stockInDate = stockInItem.stockInDate != null ? moment(stockInItem.stockInDate) : null;
            });
        }
        return res;
    }
}
