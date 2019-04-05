import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITrainingInformation } from 'app/shared/model/training-information.model';

type EntityResponseType = HttpResponse<ITrainingInformation>;
type EntityArrayResponseType = HttpResponse<ITrainingInformation[]>;

@Injectable({ providedIn: 'root' })
export class TrainingInformationService {
    public resourceUrl = SERVER_API_URL + 'api/training-informations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/training-informations';

    constructor(protected http: HttpClient) {}

    create(trainingInformation: ITrainingInformation): Observable<EntityResponseType> {
        return this.http.post<ITrainingInformation>(this.resourceUrl, trainingInformation, { observe: 'response' });
    }

    update(trainingInformation: ITrainingInformation): Observable<EntityResponseType> {
        return this.http.put<ITrainingInformation>(this.resourceUrl, trainingInformation, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITrainingInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITrainingInformation[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITrainingInformation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
