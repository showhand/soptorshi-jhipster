import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProvidentFund } from 'app/shared/model/provident-fund.model';

type EntityResponseType = HttpResponse<IProvidentFund>;
type EntityArrayResponseType = HttpResponse<IProvidentFund[]>;

@Injectable({ providedIn: 'root' })
export class ProvidentFundService {
    public resourceUrl = SERVER_API_URL + 'api/provident-funds';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/provident-funds';

    constructor(protected http: HttpClient) {}

    create(providentFund: IProvidentFund): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(providentFund);
        return this.http
            .post<IProvidentFund>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(providentFund: IProvidentFund): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(providentFund);
        return this.http
            .put<IProvidentFund>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProvidentFund>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProvidentFund[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProvidentFund[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(providentFund: IProvidentFund): IProvidentFund {
        const copy: IProvidentFund = Object.assign({}, providentFund, {
            startDate:
                providentFund.startDate != null && providentFund.startDate.isValid() ? providentFund.startDate.format(DATE_FORMAT) : null,
            modifiedOn:
                providentFund.modifiedOn != null && providentFund.modifiedOn.isValid() ? providentFund.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((providentFund: IProvidentFund) => {
                providentFund.startDate = providentFund.startDate != null ? moment(providentFund.startDate) : null;
                providentFund.modifiedOn = providentFund.modifiedOn != null ? moment(providentFund.modifiedOn) : null;
            });
        }
        return res;
    }
}
