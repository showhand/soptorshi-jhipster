import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISupplyOrder } from 'app/shared/model/supply-order.model';

type EntityResponseType = HttpResponse<ISupplyOrder>;
type EntityArrayResponseType = HttpResponse<ISupplyOrder[]>;

@Injectable({ providedIn: 'root' })
export class SupplyOrderService {
    public resourceUrl = SERVER_API_URL + 'api/supply-orders';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-orders';

    constructor(protected http: HttpClient) {}

    create(supplyOrder: ISupplyOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyOrder);
        return this.http
            .post<ISupplyOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(supplyOrder: ISupplyOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(supplyOrder);
        return this.http
            .put<ISupplyOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISupplyOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISupplyOrder[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(supplyOrder: ISupplyOrder): ISupplyOrder {
        const copy: ISupplyOrder = Object.assign({}, supplyOrder, {
            dateOfOrder:
                supplyOrder.dateOfOrder != null && supplyOrder.dateOfOrder.isValid() ? supplyOrder.dateOfOrder.format(DATE_FORMAT) : null,
            createdOn: supplyOrder.createdOn != null && supplyOrder.createdOn.isValid() ? supplyOrder.createdOn.toJSON() : null,
            updatedOn: supplyOrder.updatedOn != null && supplyOrder.updatedOn.isValid() ? supplyOrder.updatedOn.toJSON() : null,
            deliveryDate:
                supplyOrder.deliveryDate != null && supplyOrder.deliveryDate.isValid() ? supplyOrder.deliveryDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateOfOrder = res.body.dateOfOrder != null ? moment(res.body.dateOfOrder) : null;
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
            res.body.deliveryDate = res.body.deliveryDate != null ? moment(res.body.deliveryDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((supplyOrder: ISupplyOrder) => {
                supplyOrder.dateOfOrder = supplyOrder.dateOfOrder != null ? moment(supplyOrder.dateOfOrder) : null;
                supplyOrder.createdOn = supplyOrder.createdOn != null ? moment(supplyOrder.createdOn) : null;
                supplyOrder.updatedOn = supplyOrder.updatedOn != null ? moment(supplyOrder.updatedOn) : null;
                supplyOrder.deliveryDate = supplyOrder.deliveryDate != null ? moment(supplyOrder.deliveryDate) : null;
            });
        }
        return res;
    }
}
