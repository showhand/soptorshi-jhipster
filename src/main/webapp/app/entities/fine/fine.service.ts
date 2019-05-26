import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFine } from 'app/shared/model/fine.model';

type EntityResponseType = HttpResponse<IFine>;
type EntityArrayResponseType = HttpResponse<IFine[]>;

@Injectable({ providedIn: 'root' })
export class FineService {
    public resourceUrl = SERVER_API_URL + 'api/fines';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/fines';

    constructor(protected http: HttpClient) {}

    create(fine: IFine): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fine);
        return this.http
            .post<IFine>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(fine: IFine): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(fine);
        return this.http
            .put<IFine>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFine>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFine[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFine[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(fine: IFine): IFine {
        const copy: IFine = Object.assign({}, fine, {
            fineDate: fine.fineDate != null && fine.fineDate.isValid() ? fine.fineDate.format(DATE_FORMAT) : null,
            modifiedDate: fine.modifiedDate != null && fine.modifiedDate.isValid() ? fine.modifiedDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fineDate = res.body.fineDate != null ? moment(res.body.fineDate) : null;
            res.body.modifiedDate = res.body.modifiedDate != null ? moment(res.body.modifiedDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((fine: IFine) => {
                fine.fineDate = fine.fineDate != null ? moment(fine.fineDate) : null;
                fine.modifiedDate = fine.modifiedDate != null ? moment(fine.modifiedDate) : null;
            });
        }
        return res;
    }
}
