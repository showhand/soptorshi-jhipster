import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
        return this.http.post<ILeaveAttachment>(this.resourceUrl, leaveAttachment, { observe: 'response' });
    }

    update(leaveAttachment: ILeaveAttachment): Observable<EntityResponseType> {
        return this.http.put<ILeaveAttachment>(this.resourceUrl, leaveAttachment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILeaveAttachment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILeaveAttachment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILeaveAttachment[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
