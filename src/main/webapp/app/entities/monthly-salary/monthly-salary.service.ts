import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMonthlySalary } from 'app/shared/model/monthly-salary.model';

type EntityResponseType = HttpResponse<IMonthlySalary>;
type EntityArrayResponseType = HttpResponse<IMonthlySalary[]>;

@Injectable({ providedIn: 'root' })
export class MonthlySalaryService {
    public resourceUrl = SERVER_API_URL + 'api/monthly-salaries';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/monthly-salaries';

    constructor(protected http: HttpClient) {}

    create(monthlySalary: IMonthlySalary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(monthlySalary);
        return this.http
            .post<IMonthlySalary>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(monthlySalary: IMonthlySalary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(monthlySalary);
        return this.http
            .put<IMonthlySalary>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMonthlySalary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMonthlySalary[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMonthlySalary[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(monthlySalary: IMonthlySalary): IMonthlySalary {
        const copy: IMonthlySalary = Object.assign({}, monthlySalary, {
            modifiedOn:
                monthlySalary.modifiedOn != null && monthlySalary.modifiedOn.isValid() ? monthlySalary.modifiedOn.format(DATE_FORMAT) : null
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
            res.body.forEach((monthlySalary: IMonthlySalary) => {
                monthlySalary.modifiedOn = monthlySalary.modifiedOn != null ? moment(monthlySalary.modifiedOn) : null;
            });
        }
        return res;
    }
}
