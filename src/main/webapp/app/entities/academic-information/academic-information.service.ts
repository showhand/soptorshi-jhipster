import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAcademicInformation } from 'app/shared/model/academic-information.model';

type EntityResponseType = HttpResponse<IAcademicInformation>;
type EntityArrayResponseType = HttpResponse<IAcademicInformation[]>;

@Injectable({ providedIn: 'root' })
export class AcademicInformationService {
    public resourceUrl = SERVER_API_URL + 'api/academic-informations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/academic-informations';

    constructor(protected http: HttpClient) {}

    create(academicInformation: IAcademicInformation): Observable<EntityResponseType> {
        return this.http.post<IAcademicInformation>(this.resourceUrl, academicInformation, { observe: 'response' });
    }

    update(academicInformation: IAcademicInformation): Observable<EntityResponseType> {
        return this.http.put<IAcademicInformation>(this.resourceUrl, academicInformation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAcademicInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAcademicInformation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAcademicInformation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
