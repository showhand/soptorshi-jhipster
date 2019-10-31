import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IItemSubCategory } from 'app/shared/model/item-sub-category.model';
import { ItemSubCategoryService } from 'app/entities/item-sub-category';

type EntityResponseType = HttpResponse<IItemSubCategory>;
type EntityArrayResponseType = HttpResponse<IItemSubCategory[]>;

@Injectable({ providedIn: 'root' })
export class ItemSubCategoryExtendedService extends ItemSubCategoryService {
    public resourceUrl = SERVER_API_URL + 'api/item-sub-categories';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/item-sub-categories';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(itemSubCategory: IItemSubCategory): Observable<EntityResponseType> {
        return this.http.post<IItemSubCategory>(this.resourceUrl, itemSubCategory, { observe: 'response' });
    }

    update(itemSubCategory: IItemSubCategory): Observable<EntityResponseType> {
        return this.http.put<IItemSubCategory>(this.resourceUrl, itemSubCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IItemSubCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IItemSubCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IItemSubCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}