import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductCategory } from 'app/shared/model/product-category.model';

type EntityResponseType = HttpResponse<IProductCategory>;
type EntityArrayResponseType = HttpResponse<IProductCategory[]>;

@Injectable({ providedIn: 'root' })
export class ProductCategoryService {
    public resourceUrl = SERVER_API_URL + 'api/product-categories';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/product-categories';

    constructor(protected http: HttpClient) {}

    create(productCategory: IProductCategory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(productCategory);
        return this.http
            .post<IProductCategory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(productCategory: IProductCategory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(productCategory);
        return this.http
            .put<IProductCategory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProductCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProductCategory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProductCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(productCategory: IProductCategory): IProductCategory {
        const copy: IProductCategory = Object.assign({}, productCategory, {
            modifiedOn:
                productCategory.modifiedOn != null && productCategory.modifiedOn.isValid()
                    ? productCategory.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((productCategory: IProductCategory) => {
                productCategory.modifiedOn = productCategory.modifiedOn != null ? moment(productCategory.modifiedOn) : null;
            });
        }
        return res;
    }
}
