import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseOrderVoucherRelation } from 'app/shared/model/purchase-order-voucher-relation.model';

type EntityResponseType = HttpResponse<IPurchaseOrderVoucherRelation>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrderVoucherRelation[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrderVoucherRelationService {
    public resourceUrl = SERVER_API_URL + 'api/purchase-order-voucher-relations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/purchase-order-voucher-relations';

    constructor(protected http: HttpClient) {}

    create(purchaseOrderVoucherRelation: IPurchaseOrderVoucherRelation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseOrderVoucherRelation);
        return this.http
            .post<IPurchaseOrderVoucherRelation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(purchaseOrderVoucherRelation: IPurchaseOrderVoucherRelation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseOrderVoucherRelation);
        return this.http
            .put<IPurchaseOrderVoucherRelation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPurchaseOrderVoucherRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseOrderVoucherRelation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseOrderVoucherRelation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(purchaseOrderVoucherRelation: IPurchaseOrderVoucherRelation): IPurchaseOrderVoucherRelation {
        const copy: IPurchaseOrderVoucherRelation = Object.assign({}, purchaseOrderVoucherRelation, {
            modifiedOn:
                purchaseOrderVoucherRelation.modifiedOn != null && purchaseOrderVoucherRelation.modifiedOn.isValid()
                    ? purchaseOrderVoucherRelation.modifiedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((purchaseOrderVoucherRelation: IPurchaseOrderVoucherRelation) => {
                purchaseOrderVoucherRelation.modifiedOn =
                    purchaseOrderVoucherRelation.modifiedOn != null ? moment(purchaseOrderVoucherRelation.modifiedOn) : null;
            });
        }
        return res;
    }
}
