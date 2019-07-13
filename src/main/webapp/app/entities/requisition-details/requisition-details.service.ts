import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';

type EntityResponseType = HttpResponse<IRequisitionDetails>;
type EntityArrayResponseType = HttpResponse<IRequisitionDetails[]>;

@Injectable({ providedIn: 'root' })
export class RequisitionDetailsService {
    public resourceUrl = SERVER_API_URL + 'api/requisition-details';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/requisition-details';

    constructor(protected http: HttpClient) {}

    create(requisitionDetails: IRequisitionDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisitionDetails);
        return this.http
            .post<IRequisitionDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(requisitionDetails: IRequisitionDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisitionDetails);
        return this.http
            .put<IRequisitionDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRequisitionDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRequisitionDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRequisitionDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(requisitionDetails: IRequisitionDetails): IRequisitionDetails {
        const copy: IRequisitionDetails = Object.assign({}, requisitionDetails, {
            requiredOn:
                requisitionDetails.requiredOn != null && requisitionDetails.requiredOn.isValid()
                    ? requisitionDetails.requiredOn.format(DATE_FORMAT)
                    : null,
            estimatedDate:
                requisitionDetails.estimatedDate != null && requisitionDetails.estimatedDate.isValid()
                    ? requisitionDetails.estimatedDate.format(DATE_FORMAT)
                    : null,
            modifiedOn:
                requisitionDetails.modifiedOn != null && requisitionDetails.modifiedOn.isValid()
                    ? requisitionDetails.modifiedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.requiredOn = res.body.requiredOn != null ? moment(res.body.requiredOn) : null;
            res.body.estimatedDate = res.body.estimatedDate != null ? moment(res.body.estimatedDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((requisitionDetails: IRequisitionDetails) => {
                requisitionDetails.requiredOn = requisitionDetails.requiredOn != null ? moment(requisitionDetails.requiredOn) : null;
                requisitionDetails.estimatedDate =
                    requisitionDetails.estimatedDate != null ? moment(requisitionDetails.estimatedDate) : null;
                requisitionDetails.modifiedOn = requisitionDetails.modifiedOn != null ? moment(requisitionDetails.modifiedOn) : null;
            });
        }
        return res;
    }
}
