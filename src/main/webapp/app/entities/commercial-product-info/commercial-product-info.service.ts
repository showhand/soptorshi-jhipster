import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialProductInfo } from 'app/shared/model/commercial-product-info.model';

type EntityResponseType = HttpResponse<ICommercialProductInfo>;
type EntityArrayResponseType = HttpResponse<ICommercialProductInfo[]>;

@Injectable({ providedIn: 'root' })
export class CommercialProductInfoService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-product-infos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-product-infos';

    constructor(protected http: HttpClient) {}

    create(commercialProductInfo: ICommercialProductInfo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialProductInfo);
        return this.http
            .post<ICommercialProductInfo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialProductInfo: ICommercialProductInfo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialProductInfo);
        return this.http
            .put<ICommercialProductInfo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialProductInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialProductInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialProductInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialProductInfo: ICommercialProductInfo): ICommercialProductInfo {
        const copy: ICommercialProductInfo = Object.assign({}, commercialProductInfo, {
            createdOn:
                commercialProductInfo.createdOn != null && commercialProductInfo.createdOn.isValid()
                    ? commercialProductInfo.createdOn.toJSON()
                    : null,
            updatedOn:
                commercialProductInfo.updatedOn != null && commercialProductInfo.updatedOn.isValid()
                    ? commercialProductInfo.updatedOn.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialProductInfo: ICommercialProductInfo) => {
                commercialProductInfo.createdOn = commercialProductInfo.createdOn != null ? moment(commercialProductInfo.createdOn) : null;
                commercialProductInfo.updatedOn = commercialProductInfo.updatedOn != null ? moment(commercialProductInfo.updatedOn) : null;
            });
        }
        return res;
    }
}
