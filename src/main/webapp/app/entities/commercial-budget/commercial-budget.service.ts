import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialBudget } from 'app/shared/model/commercial-budget.model';

type EntityResponseType = HttpResponse<ICommercialBudget>;
type EntityArrayResponseType = HttpResponse<ICommercialBudget[]>;

@Injectable({ providedIn: 'root' })
export class CommercialBudgetService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-budgets';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-budgets';

    constructor(protected http: HttpClient) {}

    create(commercialBudget: ICommercialBudget): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialBudget);
        return this.http
            .post<ICommercialBudget>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialBudget: ICommercialBudget): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialBudget);
        return this.http
            .put<ICommercialBudget>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialBudget>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialBudget[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialBudget[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialBudget: ICommercialBudget): ICommercialBudget {
        const copy: ICommercialBudget = Object.assign({}, commercialBudget, {
            budgetDate:
                commercialBudget.budgetDate != null && commercialBudget.budgetDate.isValid()
                    ? commercialBudget.budgetDate.format(DATE_FORMAT)
                    : null,
            createdOn:
                commercialBudget.createdOn != null && commercialBudget.createdOn.isValid() ? commercialBudget.createdOn.toJSON() : null,
            updatedOn:
                commercialBudget.updatedOn != null && commercialBudget.updatedOn.isValid() ? commercialBudget.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.budgetDate = res.body.budgetDate != null ? moment(res.body.budgetDate) : null;
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialBudget: ICommercialBudget) => {
                commercialBudget.budgetDate = commercialBudget.budgetDate != null ? moment(commercialBudget.budgetDate) : null;
                commercialBudget.createdOn = commercialBudget.createdOn != null ? moment(commercialBudget.createdOn) : null;
                commercialBudget.updatedOn = commercialBudget.updatedOn != null ? moment(commercialBudget.updatedOn) : null;
            });
        }
        return res;
    }
}
