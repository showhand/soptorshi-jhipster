import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialPo } from 'app/shared/model/commercial-po.model';

type EntityResponseType = HttpResponse<ICommercialPo>;
type EntityArrayResponseType = HttpResponse<ICommercialPo[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPoService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-pos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-pos';

    constructor(protected http: HttpClient) {}

    create(commercialPo: ICommercialPo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPo);
        return this.http
            .post<ICommercialPo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialPo: ICommercialPo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPo);
        return this.http
            .put<ICommercialPo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialPo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialPo: ICommercialPo): ICommercialPo {
        const copy: ICommercialPo = Object.assign({}, commercialPo, {
            purchaseOrderDate:
                commercialPo.purchaseOrderDate != null && commercialPo.purchaseOrderDate.isValid()
                    ? commercialPo.purchaseOrderDate.format(DATE_FORMAT)
                    : null,
            shipmentDate:
                commercialPo.shipmentDate != null && commercialPo.shipmentDate.isValid()
                    ? commercialPo.shipmentDate.format(DATE_FORMAT)
                    : null,
            createdOn: commercialPo.createdOn != null && commercialPo.createdOn.isValid() ? commercialPo.createdOn.toJSON() : null,
            updatedOn: commercialPo.updatedOn != null && commercialPo.updatedOn.isValid() ? commercialPo.updatedOn.toJSON() : null
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
            res.body.forEach((commercialPo: ICommercialPo) => {
                commercialPo.purchaseOrderDate = commercialPo.purchaseOrderDate != null ? moment(commercialPo.purchaseOrderDate) : null;
                commercialPo.shipmentDate = commercialPo.shipmentDate != null ? moment(commercialPo.shipmentDate) : null;
                commercialPo.createdOn = commercialPo.createdOn != null ? moment(commercialPo.createdOn) : null;
                commercialPo.updatedOn = commercialPo.updatedOn != null ? moment(commercialPo.updatedOn) : null;
            });
        }
        return res;
    }
}
