import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReferenceInformation } from 'app/shared/model/reference-information.model';

type EntityResponseType = HttpResponse<IReferenceInformation>;
type EntityArrayResponseType = HttpResponse<IReferenceInformation[]>;

@Injectable({ providedIn: 'root' })
export class ReferenceInformationService {
    public resourceUrl = SERVER_API_URL + 'api/reference-informations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/reference-informations';

    constructor(protected http: HttpClient) {}

    create(referenceInformation: IReferenceInformation): Observable<EntityResponseType> {
        return this.http.post<IReferenceInformation>(this.resourceUrl, referenceInformation, { observe: 'response' });
    }

    update(referenceInformation: IReferenceInformation): Observable<EntityResponseType> {
        return this.http.put<IReferenceInformation>(this.resourceUrl, referenceInformation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReferenceInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReferenceInformation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReferenceInformation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
