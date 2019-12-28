import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';

type EntityResponseType = HttpResponse<IPurchaseOrderMessages>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrderMessages[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrderMessagesService {
    public resourceUrl = SERVER_API_URL + 'api/purchase-order-messages';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/purchase-order-messages';

    constructor(protected http: HttpClient) {}

    create(purchaseOrderMessages: IPurchaseOrderMessages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseOrderMessages);
        return this.http
            .post<IPurchaseOrderMessages>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(purchaseOrderMessages: IPurchaseOrderMessages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseOrderMessages);
        return this.http
            .put<IPurchaseOrderMessages>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPurchaseOrderMessages>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseOrderMessages[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPurchaseOrderMessages[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(purchaseOrderMessages: IPurchaseOrderMessages): IPurchaseOrderMessages {
        const copy: IPurchaseOrderMessages = Object.assign({}, purchaseOrderMessages, {
            commentedOn:
                purchaseOrderMessages.commentedOn != null && purchaseOrderMessages.commentedOn.isValid()
                    ? purchaseOrderMessages.commentedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.commentedOn = res.body.commentedOn != null ? moment(res.body.commentedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((purchaseOrderMessages: IPurchaseOrderMessages) => {
                purchaseOrderMessages.commentedOn =
                    purchaseOrderMessages.commentedOn != null ? moment(purchaseOrderMessages.commentedOn) : null;
            });
        }
        return res;
    }
}
