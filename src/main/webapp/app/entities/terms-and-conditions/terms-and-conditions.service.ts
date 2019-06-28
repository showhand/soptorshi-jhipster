import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITermsAndConditions } from 'app/shared/model/terms-and-conditions.model';

type EntityResponseType = HttpResponse<ITermsAndConditions>;
type EntityArrayResponseType = HttpResponse<ITermsAndConditions[]>;

@Injectable({ providedIn: 'root' })
export class TermsAndConditionsService {
    public resourceUrl = SERVER_API_URL + 'api/terms-and-conditions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/terms-and-conditions';

    constructor(protected http: HttpClient) {}

    create(termsAndConditions: ITermsAndConditions): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(termsAndConditions);
        return this.http
            .post<ITermsAndConditions>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(termsAndConditions: ITermsAndConditions): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(termsAndConditions);
        return this.http
            .put<ITermsAndConditions>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITermsAndConditions>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITermsAndConditions[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITermsAndConditions[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(termsAndConditions: ITermsAndConditions): ITermsAndConditions {
        const copy: ITermsAndConditions = Object.assign({}, termsAndConditions, {
            modifiedOn:
                termsAndConditions.modifiedOn != null && termsAndConditions.modifiedOn.isValid()
                    ? termsAndConditions.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((termsAndConditions: ITermsAndConditions) => {
                termsAndConditions.modifiedOn = termsAndConditions.modifiedOn != null ? moment(termsAndConditions.modifiedOn) : null;
            });
        }
        return res;
    }
}
