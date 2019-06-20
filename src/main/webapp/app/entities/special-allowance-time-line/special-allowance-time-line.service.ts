import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISpecialAllowanceTimeLine } from 'app/shared/model/special-allowance-time-line.model';

type EntityResponseType = HttpResponse<ISpecialAllowanceTimeLine>;
type EntityArrayResponseType = HttpResponse<ISpecialAllowanceTimeLine[]>;

@Injectable({ providedIn: 'root' })
export class SpecialAllowanceTimeLineService {
    public resourceUrl = SERVER_API_URL + 'api/special-allowance-time-lines';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/special-allowance-time-lines';

    constructor(protected http: HttpClient) {}

    create(specialAllowanceTimeLine: ISpecialAllowanceTimeLine): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(specialAllowanceTimeLine);
        return this.http
            .post<ISpecialAllowanceTimeLine>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(specialAllowanceTimeLine: ISpecialAllowanceTimeLine): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(specialAllowanceTimeLine);
        return this.http
            .put<ISpecialAllowanceTimeLine>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISpecialAllowanceTimeLine>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISpecialAllowanceTimeLine[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISpecialAllowanceTimeLine[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(specialAllowanceTimeLine: ISpecialAllowanceTimeLine): ISpecialAllowanceTimeLine {
        const copy: ISpecialAllowanceTimeLine = Object.assign({}, specialAllowanceTimeLine, {
            modifiedOn:
                specialAllowanceTimeLine.modifiedOn != null && specialAllowanceTimeLine.modifiedOn.isValid()
                    ? specialAllowanceTimeLine.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((specialAllowanceTimeLine: ISpecialAllowanceTimeLine) => {
                specialAllowanceTimeLine.modifiedOn =
                    specialAllowanceTimeLine.modifiedOn != null ? moment(specialAllowanceTimeLine.modifiedOn) : null;
            });
        }
        return res;
    }
}
