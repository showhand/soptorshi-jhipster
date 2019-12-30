import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';

type EntityResponseType = HttpResponse<IRequisitionVoucherRelation>;
type EntityArrayResponseType = HttpResponse<IRequisitionVoucherRelation[]>;

@Injectable({ providedIn: 'root' })
export class RequisitionVoucherRelationService {
    public resourceUrl = SERVER_API_URL + 'api/requisition-voucher-relations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/requisition-voucher-relations';

    constructor(protected http: HttpClient) {}

    create(requisitionVoucherRelation: IRequisitionVoucherRelation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisitionVoucherRelation);
        return this.http
            .post<IRequisitionVoucherRelation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(requisitionVoucherRelation: IRequisitionVoucherRelation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisitionVoucherRelation);
        return this.http
            .put<IRequisitionVoucherRelation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRequisitionVoucherRelation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRequisitionVoucherRelation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRequisitionVoucherRelation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(requisitionVoucherRelation: IRequisitionVoucherRelation): IRequisitionVoucherRelation {
        const copy: IRequisitionVoucherRelation = Object.assign({}, requisitionVoucherRelation, {
            modifiedOn:
                requisitionVoucherRelation.modifiedOn != null && requisitionVoucherRelation.modifiedOn.isValid()
                    ? requisitionVoucherRelation.modifiedOn.format(DATE_FORMAT)
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
            res.body.forEach((requisitionVoucherRelation: IRequisitionVoucherRelation) => {
                requisitionVoucherRelation.modifiedOn =
                    requisitionVoucherRelation.modifiedOn != null ? moment(requisitionVoucherRelation.modifiedOn) : null;
            });
        }
        return res;
    }
}
