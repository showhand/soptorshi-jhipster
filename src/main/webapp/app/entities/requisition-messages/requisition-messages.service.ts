import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';

type EntityResponseType = HttpResponse<IRequisitionMessages>;
type EntityArrayResponseType = HttpResponse<IRequisitionMessages[]>;

@Injectable({ providedIn: 'root' })
export class RequisitionMessagesService {
    public resourceUrl = SERVER_API_URL + 'api/requisition-messages';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/requisition-messages';

    constructor(protected http: HttpClient) {}

    create(requisitionMessages: IRequisitionMessages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisitionMessages);
        return this.http
            .post<IRequisitionMessages>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(requisitionMessages: IRequisitionMessages): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(requisitionMessages);
        return this.http
            .put<IRequisitionMessages>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRequisitionMessages>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRequisitionMessages[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRequisitionMessages[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(requisitionMessages: IRequisitionMessages): IRequisitionMessages {
        const copy: IRequisitionMessages = Object.assign({}, requisitionMessages, {
            commentedOn:
                requisitionMessages.commentedOn != null && requisitionMessages.commentedOn.isValid()
                    ? requisitionMessages.commentedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.commentedOn = res.body.commentedOn != null ? moment(res.body.commentedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((requisitionMessages: IRequisitionMessages) => {
                requisitionMessages.commentedOn = requisitionMessages.commentedOn != null ? moment(requisitionMessages.commentedOn) : null;
            });
        }
        return res;
    }
}
