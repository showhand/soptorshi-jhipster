import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';

type EntityResponseType = HttpResponse<ICommercialWorkOrder>;
type EntityArrayResponseType = HttpResponse<ICommercialWorkOrder[]>;

@Injectable({ providedIn: 'root' })
export class CommercialWorkOrderService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-work-orders';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-work-orders';

    constructor(protected http: HttpClient) {}

    create(commercialWorkOrder: ICommercialWorkOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialWorkOrder);
        return this.http
            .post<ICommercialWorkOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialWorkOrder: ICommercialWorkOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialWorkOrder);
        return this.http
            .put<ICommercialWorkOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialWorkOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialWorkOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialWorkOrder[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialWorkOrder: ICommercialWorkOrder): ICommercialWorkOrder {
        const copy: ICommercialWorkOrder = Object.assign({}, commercialWorkOrder, {
            workOrderDate:
                commercialWorkOrder.workOrderDate != null && commercialWorkOrder.workOrderDate.isValid()
                    ? commercialWorkOrder.workOrderDate.format(DATE_FORMAT)
                    : null,
            deliveryDate:
                commercialWorkOrder.deliveryDate != null && commercialWorkOrder.deliveryDate.isValid()
                    ? commercialWorkOrder.deliveryDate.format(DATE_FORMAT)
                    : null,
            createOn:
                commercialWorkOrder.createOn != null && commercialWorkOrder.createOn.isValid()
                    ? commercialWorkOrder.createOn.format(DATE_FORMAT)
                    : null,
            updatedOn:
                commercialWorkOrder.updatedOn != null && commercialWorkOrder.updatedOn.isValid()
                    ? commercialWorkOrder.updatedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.workOrderDate = res.body.workOrderDate != null ? moment(res.body.workOrderDate) : null;
            res.body.deliveryDate = res.body.deliveryDate != null ? moment(res.body.deliveryDate) : null;
            res.body.createOn = res.body.createOn != null ? moment(res.body.createOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialWorkOrder: ICommercialWorkOrder) => {
                commercialWorkOrder.workOrderDate =
                    commercialWorkOrder.workOrderDate != null ? moment(commercialWorkOrder.workOrderDate) : null;
                commercialWorkOrder.deliveryDate =
                    commercialWorkOrder.deliveryDate != null ? moment(commercialWorkOrder.deliveryDate) : null;
                commercialWorkOrder.createOn = commercialWorkOrder.createOn != null ? moment(commercialWorkOrder.createOn) : null;
                commercialWorkOrder.updatedOn = commercialWorkOrder.updatedOn != null ? moment(commercialWorkOrder.updatedOn) : null;
            });
        }
        return res;
    }
}
