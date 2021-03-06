import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';

type EntityResponseType = HttpResponse<IPurchaseOrder>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrder[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrderService {
    public resourceUrl = SERVER_API_URL + 'api/purchase-orders';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/purchase-orders';

    constructor(protected http: HttpClient) {}

    create(purchaseOrder: IPurchaseOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseOrder);
        return this.http
            .post<IPurchaseOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(purchaseOrder: IPurchaseOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseOrder);
        return this.http
            .put<IPurchaseOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPurchaseOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseOrder[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(purchaseOrder: IPurchaseOrder): IPurchaseOrder {
        const copy: IPurchaseOrder = Object.assign({}, purchaseOrder, {
            issueDate:
                purchaseOrder.issueDate != null && purchaseOrder.issueDate.isValid() ? purchaseOrder.issueDate.format(DATE_FORMAT) : null,
            modifiedOn:
                purchaseOrder.modifiedOn != null && purchaseOrder.modifiedOn.isValid() ? purchaseOrder.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.issueDate = res.body.issueDate != null ? moment(res.body.issueDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((purchaseOrder: IPurchaseOrder) => {
                purchaseOrder.issueDate = purchaseOrder.issueDate != null ? moment(purchaseOrder.issueDate) : null;
                purchaseOrder.modifiedOn = purchaseOrder.modifiedOn != null ? moment(purchaseOrder.modifiedOn) : null;
            });
        }
        return res;
    }
}
