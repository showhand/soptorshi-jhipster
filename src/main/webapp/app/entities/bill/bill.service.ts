import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBill } from 'app/shared/model/bill.model';

type EntityResponseType = HttpResponse<IBill>;
type EntityArrayResponseType = HttpResponse<IBill[]>;

@Injectable({ providedIn: 'root' })
export class BillService {
    public resourceUrl = SERVER_API_URL + 'api/bills';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/bills';

    constructor(protected http: HttpClient) {}

    create(bill: IBill): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bill);
        return this.http
            .post<IBill>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(bill: IBill): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bill);
        return this.http
            .put<IBill>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBill>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBill[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBill[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(bill: IBill): IBill {
        const copy: IBill = Object.assign({}, bill, {
            modifiedDate: bill.modifiedDate != null && bill.modifiedDate.isValid() ? bill.modifiedDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.modifiedDate = res.body.modifiedDate != null ? moment(res.body.modifiedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((bill: IBill) => {
                bill.modifiedDate = bill.modifiedDate != null ? moment(bill.modifiedDate) : null;
            });
        }
        return res;
    }
}
