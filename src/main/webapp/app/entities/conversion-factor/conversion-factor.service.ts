import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';

type EntityResponseType = HttpResponse<IConversionFactor>;
type EntityArrayResponseType = HttpResponse<IConversionFactor[]>;

@Injectable({ providedIn: 'root' })
export class ConversionFactorService {
    public resourceUrl = SERVER_API_URL + 'api/conversion-factors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/conversion-factors';

    constructor(protected http: HttpClient) {}

    create(conversionFactor: IConversionFactor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(conversionFactor);
        return this.http
            .post<IConversionFactor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(conversionFactor: IConversionFactor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(conversionFactor);
        return this.http
            .put<IConversionFactor>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IConversionFactor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IConversionFactor[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IConversionFactor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(conversionFactor: IConversionFactor): IConversionFactor {
        const copy: IConversionFactor = Object.assign({}, conversionFactor, {
            modifiedOn:
                conversionFactor.modifiedOn != null && conversionFactor.modifiedOn.isValid()
                    ? conversionFactor.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((conversionFactor: IConversionFactor) => {
                conversionFactor.modifiedOn = conversionFactor.modifiedOn != null ? moment(conversionFactor.modifiedOn) : null;
            });
        }
        return res;
    }
}
