import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdvance } from 'app/shared/model/advance.model';

type EntityResponseType = HttpResponse<IAdvance>;
type EntityArrayResponseType = HttpResponse<IAdvance[]>;

@Injectable({ providedIn: 'root' })
export class AdvanceService {
    public resourceUrl = SERVER_API_URL + 'api/advances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/advances';

    constructor(protected http: HttpClient) {}

    create(advance: IAdvance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(advance);
        return this.http
            .post<IAdvance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(advance: IAdvance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(advance);
        return this.http
            .put<IAdvance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAdvance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAdvance[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAdvance[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(advance: IAdvance): IAdvance {
        const copy: IAdvance = Object.assign({}, advance, {
            providedOn: advance.providedOn != null && advance.providedOn.isValid() ? advance.providedOn.format(DATE_FORMAT) : null,
            modifiedOn: advance.modifiedOn != null && advance.modifiedOn.isValid() ? advance.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.providedOn = res.body.providedOn != null ? moment(res.body.providedOn) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((advance: IAdvance) => {
                advance.providedOn = advance.providedOn != null ? moment(advance.providedOn) : null;
                advance.modifiedOn = advance.modifiedOn != null ? moment(advance.modifiedOn) : null;
            });
        }
        return res;
    }
}
