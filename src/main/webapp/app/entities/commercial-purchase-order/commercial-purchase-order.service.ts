import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';

type EntityResponseType = HttpResponse<ICommercialPurchaseOrder>;
type EntityArrayResponseType = HttpResponse<ICommercialPurchaseOrder[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPurchaseOrderService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-purchase-orders';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-purchase-orders';

    constructor(protected http: HttpClient) {}

    create(commercialPurchaseOrder: ICommercialPurchaseOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPurchaseOrder);
        return this.http
            .post<ICommercialPurchaseOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialPurchaseOrder: ICommercialPurchaseOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPurchaseOrder);
        return this.http
            .put<ICommercialPurchaseOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialPurchaseOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPurchaseOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPurchaseOrder[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialPurchaseOrder: ICommercialPurchaseOrder): ICommercialPurchaseOrder {
        const copy: ICommercialPurchaseOrder = Object.assign({}, commercialPurchaseOrder, {
            purchaseOrderDate:
                commercialPurchaseOrder.purchaseOrderDate != null && commercialPurchaseOrder.purchaseOrderDate.isValid()
                    ? commercialPurchaseOrder.purchaseOrderDate.format(DATE_FORMAT)
                    : null,
            shipmentDate:
                commercialPurchaseOrder.shipmentDate != null && commercialPurchaseOrder.shipmentDate.isValid()
                    ? commercialPurchaseOrder.shipmentDate.format(DATE_FORMAT)
                    : null,
            createdOn:
                commercialPurchaseOrder.createdOn != null && commercialPurchaseOrder.createdOn.isValid()
                    ? commercialPurchaseOrder.createdOn.format(DATE_FORMAT)
                    : null,
            updatedOn:
                commercialPurchaseOrder.updatedOn != null && commercialPurchaseOrder.updatedOn.isValid()
                    ? commercialPurchaseOrder.updatedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.purchaseOrderDate = res.body.purchaseOrderDate != null ? moment(res.body.purchaseOrderDate) : null;
            res.body.shipmentDate = res.body.shipmentDate != null ? moment(res.body.shipmentDate) : null;
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialPurchaseOrder: ICommercialPurchaseOrder) => {
                commercialPurchaseOrder.purchaseOrderDate =
                    commercialPurchaseOrder.purchaseOrderDate != null ? moment(commercialPurchaseOrder.purchaseOrderDate) : null;
                commercialPurchaseOrder.shipmentDate =
                    commercialPurchaseOrder.shipmentDate != null ? moment(commercialPurchaseOrder.shipmentDate) : null;
                commercialPurchaseOrder.createdOn =
                    commercialPurchaseOrder.createdOn != null ? moment(commercialPurchaseOrder.createdOn) : null;
                commercialPurchaseOrder.updatedOn =
                    commercialPurchaseOrder.updatedOn != null ? moment(commercialPurchaseOrder.updatedOn) : null;
            });
        }
        return res;
    }
}
