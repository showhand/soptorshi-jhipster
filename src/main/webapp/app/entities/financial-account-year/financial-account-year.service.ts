import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFinancialAccountYear } from 'app/shared/model/financial-account-year.model';

type EntityResponseType = HttpResponse<IFinancialAccountYear>;
type EntityArrayResponseType = HttpResponse<IFinancialAccountYear[]>;

@Injectable({ providedIn: 'root' })
export class FinancialAccountYearService {
    public resourceUrl = SERVER_API_URL + 'api/financial-account-years';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/financial-account-years';

    constructor(protected http: HttpClient) {}

    create(financialAccountYear: IFinancialAccountYear): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(financialAccountYear);
        return this.http
            .post<IFinancialAccountYear>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(financialAccountYear: IFinancialAccountYear): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(financialAccountYear);
        return this.http
            .put<IFinancialAccountYear>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFinancialAccountYear>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFinancialAccountYear[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFinancialAccountYear[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(financialAccountYear: IFinancialAccountYear): IFinancialAccountYear {
        const copy: IFinancialAccountYear = Object.assign({}, financialAccountYear, {
            startDate:
                financialAccountYear.startDate != null && financialAccountYear.startDate.isValid()
                    ? financialAccountYear.startDate.format(DATE_FORMAT)
                    : null,
            endDate:
                financialAccountYear.endDate != null && financialAccountYear.endDate.isValid()
                    ? financialAccountYear.endDate.format(DATE_FORMAT)
                    : null,
            previousStartDate:
                financialAccountYear.previousStartDate != null && financialAccountYear.previousStartDate.isValid()
                    ? financialAccountYear.previousStartDate.format(DATE_FORMAT)
                    : null,
            previousEndDate:
                financialAccountYear.previousEndDate != null && financialAccountYear.previousEndDate.isValid()
                    ? financialAccountYear.previousEndDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
            res.body.previousStartDate = res.body.previousStartDate != null ? moment(res.body.previousStartDate) : null;
            res.body.previousEndDate = res.body.previousEndDate != null ? moment(res.body.previousEndDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((financialAccountYear: IFinancialAccountYear) => {
                financialAccountYear.startDate = financialAccountYear.startDate != null ? moment(financialAccountYear.startDate) : null;
                financialAccountYear.endDate = financialAccountYear.endDate != null ? moment(financialAccountYear.endDate) : null;
                financialAccountYear.previousStartDate =
                    financialAccountYear.previousStartDate != null ? moment(financialAccountYear.previousStartDate) : null;
                financialAccountYear.previousEndDate =
                    financialAccountYear.previousEndDate != null ? moment(financialAccountYear.previousEndDate) : null;
            });
        }
        return res;
    }
}
