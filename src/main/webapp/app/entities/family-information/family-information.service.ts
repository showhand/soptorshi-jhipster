import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFamilyInformation } from 'app/shared/model/family-information.model';

type EntityResponseType = HttpResponse<IFamilyInformation>;
type EntityArrayResponseType = HttpResponse<IFamilyInformation[]>;

@Injectable({ providedIn: 'root' })
export class FamilyInformationService {
    public resourceUrl = SERVER_API_URL + 'api/family-informations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/family-informations';

    constructor(protected http: HttpClient) {}

    create(familyInformation: IFamilyInformation): Observable<EntityResponseType> {
        return this.http.post<IFamilyInformation>(this.resourceUrl, familyInformation, { observe: 'response' });
    }

    update(familyInformation: IFamilyInformation): Observable<EntityResponseType> {
        return this.http.put<IFamilyInformation>(this.resourceUrl, familyInformation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFamilyInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFamilyInformation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFamilyInformation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
