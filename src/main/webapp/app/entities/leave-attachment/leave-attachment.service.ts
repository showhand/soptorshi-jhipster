import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILeaveAttachment } from 'app/shared/model/leave-attachment.model';

type EntityResponseType = HttpResponse<ILeaveAttachment>;
type EntityArrayResponseType = HttpResponse<ILeaveAttachment[]>;

@Injectable({ providedIn: 'root' })
export class LeaveAttachmentService {
    public resourceUrl = SERVER_API_URL + 'api/leave-attachments';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/leave-attachments';

    constructor(protected http: HttpClient) {}

    create(leaveAttachment: ILeaveAttachment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(leaveAttachment);
        return this.http
            .post<ILeaveAttachment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(leaveAttachment: ILeaveAttachment): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(leaveAttachment);
        return this.http
            .put<ILeaveAttachment>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILeaveAttachment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILeaveAttachment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILeaveAttachment[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(leaveAttachment: ILeaveAttachment): ILeaveAttachment {
        const copy: ILeaveAttachment = Object.assign({}, leaveAttachment, {
            createdOn: leaveAttachment.createdOn != null && leaveAttachment.createdOn.isValid() ? leaveAttachment.createdOn.toJSON() : null,
            updatedOn: leaveAttachment.updatedOn != null && leaveAttachment.updatedOn.isValid() ? leaveAttachment.updatedOn.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdOn = res.body.createdOn != null ? moment(res.body.createdOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((leaveAttachment: ILeaveAttachment) => {
                leaveAttachment.createdOn = leaveAttachment.createdOn != null ? moment(leaveAttachment.createdOn) : null;
                leaveAttachment.updatedOn = leaveAttachment.updatedOn != null ? moment(leaveAttachment.updatedOn) : null;
            });
        }
        return res;
    }
}
