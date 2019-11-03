import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';

type EntityResponseType = HttpResponse<ICommercialPurchaseOrderItem>;
type EntityArrayResponseType = HttpResponse<ICommercialPurchaseOrderItem[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPurchaseOrderItemService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-purchase-order-items';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-purchase-order-items';

    constructor(protected http: HttpClient) {}

    create(commercialPurchaseOrderItem: ICommercialPurchaseOrderItem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPurchaseOrderItem);
        return this.http
            .post<ICommercialPurchaseOrderItem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialPurchaseOrderItem: ICommercialPurchaseOrderItem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPurchaseOrderItem);
        return this.http
            .put<ICommercialPurchaseOrderItem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialPurchaseOrderItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPurchaseOrderItem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPurchaseOrderItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialPurchaseOrderItem: ICommercialPurchaseOrderItem): ICommercialPurchaseOrderItem {
        const copy: ICommercialPurchaseOrderItem = Object.assign({}, commercialPurchaseOrderItem, {
            createOn:
                commercialPurchaseOrderItem.createOn != null && commercialPurchaseOrderItem.createOn.isValid()
                    ? commercialPurchaseOrderItem.createOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createOn = res.body.createOn != null ? moment(res.body.createOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialPurchaseOrderItem: ICommercialPurchaseOrderItem) => {
                commercialPurchaseOrderItem.createOn =
                    commercialPurchaseOrderItem.createOn != null ? moment(commercialPurchaseOrderItem.createOn) : null;
            });
        }
        return res;
    }
}
