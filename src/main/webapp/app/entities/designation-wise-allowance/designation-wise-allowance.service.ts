import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDesignationWiseAllowance } from 'app/shared/model/designation-wise-allowance.model';
import { Designation } from 'app/shared/model/designation.model';

type EntityResponseType = HttpResponse<IDesignationWiseAllowance>;
type EntityArrayResponseType = HttpResponse<IDesignationWiseAllowance[]>;

@Injectable({ providedIn: 'root' })
export class DesignationWiseAllowanceService {
    public resourceUrl = SERVER_API_URL + 'api/designation-wise-allowances';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/designation-wise-allowances';
    public designationId: number;

    constructor(protected http: HttpClient) {}

    create(designationWiseAllowance: IDesignationWiseAllowance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(designationWiseAllowance);
        return this.http
            .post<IDesignationWiseAllowance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(designationWiseAllowance: IDesignationWiseAllowance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(designationWiseAllowance);
        return this.http
            .put<IDesignationWiseAllowance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IDesignationWiseAllowance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDesignationWiseAllowance[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IDesignationWiseAllowance[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(designationWiseAllowance: IDesignationWiseAllowance): IDesignationWiseAllowance {
        const copy: IDesignationWiseAllowance = Object.assign({}, designationWiseAllowance, {
            modifiedOn:
                designationWiseAllowance.modifiedOn != null && designationWiseAllowance.modifiedOn.isValid()
                    ? designationWiseAllowance.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((designationWiseAllowance: IDesignationWiseAllowance) => {
                designationWiseAllowance.modifiedOn =
                    designationWiseAllowance.modifiedOn != null ? moment(designationWiseAllowance.modifiedOn) : null;
            });
        }
        return res;
    }
}
