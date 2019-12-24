import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialAttachment } from 'app/shared/model/commercial-attachment.model';

type EntityResponseType = HttpResponse<ICommercialAttachment>;
type EntityArrayResponseType = HttpResponse<ICommercialAttachment[]>;

@Injectable({ providedIn: 'root' })
export class CommercialAttachmentService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-attachments';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-attachments';

    constructor(protected http: HttpClient) {}

    create(commercialAttachment: ICommercialAttachment): Observable<EntityResponseType> {
        return this.http.post<ICommercialAttachment>(this.resourceUrl, commercialAttachment, { observe: 'response' });
    }

    update(commercialAttachment: ICommercialAttachment): Observable<EntityResponseType> {
        return this.http.put<ICommercialAttachment>(this.resourceUrl, commercialAttachment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICommercialAttachment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICommercialAttachment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICommercialAttachment[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
