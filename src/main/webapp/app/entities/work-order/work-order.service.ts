import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWorkOrder } from 'app/shared/model/work-order.model';

type EntityResponseType = HttpResponse<IWorkOrder>;
type EntityArrayResponseType = HttpResponse<IWorkOrder[]>;

@Injectable({ providedIn: 'root' })
export class WorkOrderService {
    public resourceUrl = SERVER_API_URL + 'api/work-orders';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/work-orders';

    constructor(protected http: HttpClient) {}

    create(workOrder: IWorkOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(workOrder);
        return this.http
            .post<IWorkOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(workOrder: IWorkOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(workOrder);
        return this.http
            .put<IWorkOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWorkOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWorkOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWorkOrder[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(workOrder: IWorkOrder): IWorkOrder {
        const copy: IWorkOrder = Object.assign({}, workOrder, {
            issueDate: workOrder.issueDate != null && workOrder.issueDate.isValid() ? workOrder.issueDate.format(DATE_FORMAT) : null,
            modifiedOn: workOrder.modifiedOn != null && workOrder.modifiedOn.isValid() ? workOrder.modifiedOn.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.issueDate = res.body.issueDate != null ? moment(res.body.issueDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((workOrder: IWorkOrder) => {
                workOrder.issueDate = workOrder.issueDate != null ? moment(workOrder.issueDate) : null;
                workOrder.modifiedOn = workOrder.modifiedOn != null ? moment(workOrder.modifiedOn) : null;
            });
        }
        return res;
    }
}
