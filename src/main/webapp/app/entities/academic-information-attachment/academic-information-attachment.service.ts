import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';

type EntityResponseType = HttpResponse<IAcademicInformationAttachment>;
type EntityArrayResponseType = HttpResponse<IAcademicInformationAttachment[]>;

@Injectable({ providedIn: 'root' })
export class AcademicInformationAttachmentService {
    public resourceUrl = SERVER_API_URL + 'api/academic-information-attachments';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/academic-information-attachments';

    constructor(protected http: HttpClient) {}

    create(academicInformationAttachment: IAcademicInformationAttachment): Observable<EntityResponseType> {
        return this.http.post<IAcademicInformationAttachment>(this.resourceUrl, academicInformationAttachment, { observe: 'response' });
    }

    update(academicInformationAttachment: IAcademicInformationAttachment): Observable<EntityResponseType> {
        return this.http.put<IAcademicInformationAttachment>(this.resourceUrl, academicInformationAttachment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAcademicInformationAttachment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAcademicInformationAttachment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAcademicInformationAttachment[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
