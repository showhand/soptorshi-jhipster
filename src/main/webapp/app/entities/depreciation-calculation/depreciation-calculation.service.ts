import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepreciationCalculation } from 'app/shared/model/depreciation-calculation.model';

type EntityResponseType = HttpResponse<IDepreciationCalculation>;
type EntityArrayResponseType = HttpResponse<IDepreciationCalculation[]>;

@Injectable({ providedIn: 'root' })
export class DepreciationCalculationService {
    public resourceUrl = SERVER_API_URL + 'api/depreciation-calculations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/depreciation-calculations';

    constructor(protected http: HttpClient) {}

    create(depreciationCalculation: IDepreciationCalculation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(depreciationCalculation);
        return this.http
            .post<IDepreciationCalculation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(depreciationCalculation: IDepreciationCalculation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(depreciationCalculation);
        return this.http
            .put<IDepreciationCalculation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDepreciationCalculation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDepreciationCalculation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDepreciationCalculation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(depreciationCalculation: IDepreciationCalculation): IDepreciationCalculation {
        const copy: IDepreciationCalculation = Object.assign({}, depreciationCalculation, {
            createdOn:
                depreciationCalculation.createdOn != null && depreciationCalculation.createdOn.isValid()
                    ? depreciationCalculation.createdOn.toJSON()
                    : null,
            modifiedOn:
                depreciationCalculation.modifiedOn != null && depreciationCalculation.modifiedOn.isValid()
                    ? depreciationCalculation.modifiedOn.toJSON()
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((depreciationCalculation: IDepreciationCalculation) => {
                depreciationCalculation.createdOn =
                    depreciationCalculation.createdOn != null ? moment(depreciationCalculation.createdOn) : null;
                depreciationCalculation.modifiedOn =
                    depreciationCalculation.modifiedOn != null ? moment(depreciationCalculation.modifiedOn) : null;
            });
        }
        return res;
    }
}
