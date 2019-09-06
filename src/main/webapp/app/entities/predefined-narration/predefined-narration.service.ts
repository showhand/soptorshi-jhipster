import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPredefinedNarration } from 'app/shared/model/predefined-narration.model';

type EntityResponseType = HttpResponse<IPredefinedNarration>;
type EntityArrayResponseType = HttpResponse<IPredefinedNarration[]>;

@Injectable({ providedIn: 'root' })
export class PredefinedNarrationService {
    public resourceUrl = SERVER_API_URL + 'api/predefined-narrations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/predefined-narrations';

    constructor(protected http: HttpClient) {}

    create(predefinedNarration: IPredefinedNarration): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(predefinedNarration);
        return this.http
            .post<IPredefinedNarration>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(predefinedNarration: IPredefinedNarration): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(predefinedNarration);
        return this.http
            .put<IPredefinedNarration>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPredefinedNarration>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPredefinedNarration[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPredefinedNarration[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(predefinedNarration: IPredefinedNarration): IPredefinedNarration {
        const copy: IPredefinedNarration = Object.assign({}, predefinedNarration, {
            modifiedOn:
                predefinedNarration.modifiedOn != null && predefinedNarration.modifiedOn.isValid()
                    ? predefinedNarration.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((predefinedNarration: IPredefinedNarration) => {
                predefinedNarration.modifiedOn = predefinedNarration.modifiedOn != null ? moment(predefinedNarration.modifiedOn) : null;
            });
        }
        return res;
    }
}
