import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPeriodClose } from 'app/shared/model/period-close.model';

type EntityResponseType = HttpResponse<IPeriodClose>;
type EntityArrayResponseType = HttpResponse<IPeriodClose[]>;

@Injectable({ providedIn: 'root' })
export class PeriodCloseService {
    public resourceUrl = SERVER_API_URL + 'api/period-closes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/period-closes';

    constructor(protected http: HttpClient) {}

    create(periodClose: IPeriodClose): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(periodClose);
        return this.http
            .post<IPeriodClose>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(periodClose: IPeriodClose): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(periodClose);
        return this.http
            .put<IPeriodClose>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPeriodClose>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPeriodClose[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPeriodClose[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(periodClose: IPeriodClose): IPeriodClose {
        const copy: IPeriodClose = Object.assign({}, periodClose, {
            modifiedOn:
                periodClose.modifiedOn != null && periodClose.modifiedOn.isValid() ? periodClose.modifiedOn.format(DATE_FORMAT) : null
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
            res.body.forEach((periodClose: IPeriodClose) => {
                periodClose.modifiedOn = periodClose.modifiedOn != null ? moment(periodClose.modifiedOn) : null;
            });
        }
        return res;
    }
}
