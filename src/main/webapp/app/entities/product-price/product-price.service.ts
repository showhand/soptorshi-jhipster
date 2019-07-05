import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductPrice } from 'app/shared/model/product-price.model';

type EntityResponseType = HttpResponse<IProductPrice>;
type EntityArrayResponseType = HttpResponse<IProductPrice[]>;

@Injectable({ providedIn: 'root' })
export class ProductPriceService {
    public resourceUrl = SERVER_API_URL + 'api/product-prices';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/product-prices';

    constructor(protected http: HttpClient) {}

    create(productPrice: IProductPrice): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(productPrice);
        return this.http
            .post<IProductPrice>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(productPrice: IProductPrice): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(productPrice);
        return this.http
            .put<IProductPrice>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProductPrice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProductPrice[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProductPrice[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(productPrice: IProductPrice): IProductPrice {
        const copy: IProductPrice = Object.assign({}, productPrice, {
            priceDate:
                productPrice.priceDate != null && productPrice.priceDate.isValid() ? productPrice.priceDate.format(DATE_FORMAT) : null,
            modifiedOn:
                productPrice.modifiedOn != null && productPrice.modifiedOn.isValid() ? productPrice.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.priceDate = res.body.priceDate != null ? moment(res.body.priceDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((productPrice: IProductPrice) => {
                productPrice.priceDate = productPrice.priceDate != null ? moment(productPrice.priceDate) : null;
                productPrice.modifiedOn = productPrice.modifiedOn != null ? moment(productPrice.modifiedOn) : null;
            });
        }
        return res;
    }
}
