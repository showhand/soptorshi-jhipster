import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalaryMessages } from 'app/shared/model/salary-messages.model';

type EntityResponseType = HttpResponse<ISalaryMessages>;
type EntityArrayResponseType = HttpResponse<ISalaryMessages[]>;

@Injectable({ providedIn: 'root' })
export class SalaryMessagesService {
    public resourceUrl = SERVER_API_URL + 'api/salary-messages';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/salary-messages';

    constructor(protected http: HttpClient) {}

    create(salaryMessages: ISalaryMessages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salaryMessages);
        return this.http
            .post<ISalaryMessages>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(salaryMessages: ISalaryMessages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salaryMessages);
        return this.http
            .put<ISalaryMessages>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISalaryMessages>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISalaryMessages[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISalaryMessages[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(salaryMessages: ISalaryMessages): ISalaryMessages {
        const copy: ISalaryMessages = Object.assign({}, salaryMessages, {
            commentedOn:
                salaryMessages.commentedOn != null && salaryMessages.commentedOn.isValid()
                    ? salaryMessages.commentedOn.format(DATE_FORMAT)
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
            res.body.forEach((salaryMessages: ISalaryMessages) => {
                salaryMessages.commentedOn = salaryMessages.commentedOn != null ? moment(salaryMessages.commentedOn) : null;
            });
        }
        return res;
    }
}
