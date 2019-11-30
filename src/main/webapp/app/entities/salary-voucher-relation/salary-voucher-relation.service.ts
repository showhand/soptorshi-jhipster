import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalaryVoucherRelation } from 'app/shared/model/salary-voucher-relation.model';

type EntityResponseType = HttpResponse<ISalaryVoucherRelation>;
type EntityArrayResponseType = HttpResponse<ISalaryVoucherRelation[]>;

@Injectable({ providedIn: 'root' })
export class SalaryVoucherRelationService {
    public resourceUrl = SERVER_API_URL + 'api/salary-voucher-relations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/salary-voucher-relations';

    constructor(protected http: HttpClient) {}

    create(salaryVoucherRelation: ISalaryVoucherRelation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salaryVoucherRelation);
        return this.http
            .post<ISalaryVoucherRelation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(salaryVoucherRelation: ISalaryVoucherRelation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(salaryVoucherRelation);
        return this.http
            .put<ISalaryVoucherRelation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISalaryVoucherRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISalaryVoucherRelation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISalaryVoucherRelation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(salaryVoucherRelation: ISalaryVoucherRelation): ISalaryVoucherRelation {
        const copy: ISalaryVoucherRelation = Object.assign({}, salaryVoucherRelation, {
            modifiedOn:
                salaryVoucherRelation.modifiedOn != null && salaryVoucherRelation.modifiedOn.isValid()
                    ? salaryVoucherRelation.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((salaryVoucherRelation: ISalaryVoucherRelation) => {
                salaryVoucherRelation.modifiedOn =
                    salaryVoucherRelation.modifiedOn != null ? moment(salaryVoucherRelation.modifiedOn) : null;
            });
        }
        return res;
    }
}
