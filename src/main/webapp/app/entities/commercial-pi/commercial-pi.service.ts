import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialPi } from 'app/shared/model/commercial-pi.model';

type EntityResponseType = HttpResponse<ICommercialPi>;
type EntityArrayResponseType = HttpResponse<ICommercialPi[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPiService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-pis';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-pis';

    constructor(protected http: HttpClient) {}

    create(commercialPi: ICommercialPi): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPi);
        return this.http
            .post<ICommercialPi>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialPi: ICommercialPi): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPi);
        return this.http
            .put<ICommercialPi>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialPi>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPi[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPi[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialPi: ICommercialPi): ICommercialPi {
        const copy: ICommercialPi = Object.assign({}, commercialPi, {
            proformaDate:
                commercialPi.proformaDate != null && commercialPi.proformaDate.isValid()
                    ? commercialPi.proformaDate.format(DATE_FORMAT)
                    : null,
            createdOn: commercialPi.createdOn != null && commercialPi.createdOn.isValid() ? commercialPi.createdOn.toJSON() : null,
            updatedOn: commercialPi.updatedOn != null && commercialPi.updatedOn.isValid() ? commercialPi.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.proformaDate = res.body.proformaDate != null ? moment(res.body.proformaDate) : null;
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialPi: ICommercialPi) => {
                commercialPi.proformaDate = commercialPi.proformaDate != null ? moment(commercialPi.proformaDate) : null;
                commercialPi.createdOn = commercialPi.createdOn != null ? moment(commercialPi.createdOn) : null;
                commercialPi.updatedOn = commercialPi.updatedOn != null ? moment(commercialPi.updatedOn) : null;
            });
        }
        return res;
    }
}
