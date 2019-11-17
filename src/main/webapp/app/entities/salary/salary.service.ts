import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalary } from 'app/shared/model/salary.model';

type EntityResponseType = HttpResponse<ISalary>;
type EntityArrayResponseType = HttpResponse<ISalary[]>;

@Injectable({ providedIn: 'root' })
export class SalaryService {
    public resourceUrl = SERVER_API_URL + 'api/salaries';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/salaries';

    constructor(protected http: HttpClient) {}

    create(salary: ISalary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salary);
        return this.http
            .post<ISalary>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(salary: ISalary): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salary);
        return this.http
            .put<ISalary>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISalary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISalary[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISalary[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(salary: ISalary): ISalary {
        const copy: ISalary = Object.assign({}, salary, {
            startedOn: salary.startedOn != null && salary.startedOn.isValid() ? salary.startedOn.format(DATE_FORMAT) : null,
            endedOn: salary.endedOn != null && salary.endedOn.isValid() ? salary.endedOn.format(DATE_FORMAT) : null,
            modifiedOn: salary.modifiedOn != null && salary.modifiedOn.isValid() ? salary.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startedOn = res.body.startedOn != null ? moment(res.body.startedOn) : null;
            res.body.endedOn = res.body.endedOn != null ? moment(res.body.endedOn) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((salary: ISalary) => {
                salary.startedOn = salary.startedOn != null ? moment(salary.startedOn) : null;
                salary.endedOn = salary.endedOn != null ? moment(salary.endedOn) : null;
                salary.modifiedOn = salary.modifiedOn != null ? moment(salary.modifiedOn) : null;
            });
        }
        return res;
    }
}
