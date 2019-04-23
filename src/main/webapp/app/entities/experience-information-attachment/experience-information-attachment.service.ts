import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExperienceInformationAttachment } from 'app/shared/model/experience-information-attachment.model';

type EntityResponseType = HttpResponse<IExperienceInformationAttachment>;
type EntityArrayResponseType = HttpResponse<IExperienceInformationAttachment[]>;

@Injectable({ providedIn: 'root' })
export class ExperienceInformationAttachmentService {
    public resourceUrl = SERVER_API_URL + 'api/experience-information-attachments';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/experience-information-attachments';

    constructor(protected http: HttpClient) {}

    create(experienceInformationAttachment: IExperienceInformationAttachment): Observable<EntityResponseType> {
        return this.http.post<IExperienceInformationAttachment>(this.resourceUrl, experienceInformationAttachment, { observe: 'response' });
    }

    update(experienceInformationAttachment: IExperienceInformationAttachment): Observable<EntityResponseType> {
        return this.http.put<IExperienceInformationAttachment>(this.resourceUrl, experienceInformationAttachment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExperienceInformationAttachment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExperienceInformationAttachment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExperienceInformationAttachment[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
