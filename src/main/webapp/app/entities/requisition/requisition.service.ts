import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRequisition } from 'app/shared/model/requisition.model';

type EntityResponseType = HttpResponse<IRequisition>;
type EntityArrayResponseType = HttpResponse<IRequisition[]>;

@Injectable({ providedIn: 'root' })
export class RequisitionService {
    public resourceUrl = SERVER_API_URL + 'api/requisitions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/requisitions';

    constructor(protected http: HttpClient) {}

    create(requisition: IRequisition): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisition);
        return this.http
            .post<IRequisition>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(requisition: IRequisition): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisition);
        return this.http
            .put<IRequisition>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRequisition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRequisition[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRequisition[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(requisition: IRequisition): IRequisition {
        const copy: IRequisition = Object.assign({}, requisition, {
            requisitionDate:
                requisition.requisitionDate != null && requisition.requisitionDate.isValid()
                    ? requisition.requisitionDate.format(DATE_FORMAT)
                    : null,
            modifiedOn:
                requisition.modifiedOn != null && requisition.modifiedOn.isValid() ? requisition.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.requisitionDate = res.body.requisitionDate != null ? moment(res.body.requisitionDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((requisition: IRequisition) => {
                requisition.requisitionDate = requisition.requisitionDate != null ? moment(requisition.requisitionDate) : null;
                requisition.modifiedOn = requisition.modifiedOn != null ? moment(requisition.modifiedOn) : null;
            });
        }
        return res;
    }
}
