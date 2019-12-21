import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockInProcess } from 'app/shared/model/stock-in-process.model';

type EntityResponseType = HttpResponse<IStockInProcess>;
type EntityArrayResponseType = HttpResponse<IStockInProcess[]>;

@Injectable({ providedIn: 'root' })
export class StockInProcessService {
    public resourceUrl = SERVER_API_URL + 'api/stock-in-processes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/stock-in-processes';

    constructor(protected http: HttpClient) {}

    create(stockInProcess: IStockInProcess): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockInProcess);
        return this.http
            .post<IStockInProcess>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(stockInProcess: IStockInProcess): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(stockInProcess);
        return this.http
            .put<IStockInProcess>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IStockInProcess>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStockInProcess[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IStockInProcess[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(stockInProcess: IStockInProcess): IStockInProcess {
        const copy: IStockInProcess = Object.assign({}, stockInProcess, {
            mfgDate: stockInProcess.mfgDate != null && stockInProcess.mfgDate.isValid() ? stockInProcess.mfgDate.format(DATE_FORMAT) : null,
            expiryDate:
                stockInProcess.expiryDate != null && stockInProcess.expiryDate.isValid()
                    ? stockInProcess.expiryDate.format(DATE_FORMAT)
                    : null,
            processStartedOn:
                stockInProcess.processStartedOn != null && stockInProcess.processStartedOn.isValid()
                    ? stockInProcess.processStartedOn.toJSON()
                    : null,
            stockInDate:
                stockInProcess.stockInDate != null && stockInProcess.stockInDate.isValid() ? stockInProcess.stockInDate.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.mfgDate = res.body.mfgDate != null ? moment(res.body.mfgDate) : null;
            res.body.expiryDate = res.body.expiryDate != null ? moment(res.body.expiryDate) : null;
            res.body.processStartedOn = res.body.processStartedOn != null ? moment(res.body.processStartedOn) : null;
            res.body.stockInDate = res.body.stockInDate != null ? moment(res.body.stockInDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((stockInProcess: IStockInProcess) => {
                stockInProcess.mfgDate = stockInProcess.mfgDate != null ? moment(stockInProcess.mfgDate) : null;
                stockInProcess.expiryDate = stockInProcess.expiryDate != null ? moment(stockInProcess.expiryDate) : null;
                stockInProcess.processStartedOn = stockInProcess.processStartedOn != null ? moment(stockInProcess.processStartedOn) : null;
                stockInProcess.stockInDate = stockInProcess.stockInDate != null ? moment(stockInProcess.stockInDate) : null;
            });
        }
        return res;
    }
}
