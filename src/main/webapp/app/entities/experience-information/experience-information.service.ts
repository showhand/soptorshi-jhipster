import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExperienceInformation } from 'app/shared/model/experience-information.model';

type EntityResponseType = HttpResponse<IExperienceInformation>;
type EntityArrayResponseType = HttpResponse<IExperienceInformation[]>;

@Injectable({ providedIn: 'root' })
export class ExperienceInformationService {
    public resourceUrl = SERVER_API_URL + 'api/experience-informations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/experience-informations';

    constructor(protected http: HttpClient) {}

    create(experienceInformation: IExperienceInformation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(experienceInformation);
        return this.http
            .post<IExperienceInformation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(experienceInformation: IExperienceInformation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(experienceInformation);
        return this.http
            .put<IExperienceInformation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IExperienceInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IExperienceInformation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IExperienceInformation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(experienceInformation: IExperienceInformation): IExperienceInformation {
        const copy: IExperienceInformation = Object.assign({}, experienceInformation, {
            startDate:
                experienceInformation.startDate != null && experienceInformation.startDate.isValid()
                    ? experienceInformation.startDate.format(DATE_FORMAT)
                    : null,
            endDate:
                experienceInformation.endDate != null && experienceInformation.endDate.isValid()
                    ? experienceInformation.endDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((experienceInformation: IExperienceInformation) => {
                experienceInformation.startDate = experienceInformation.startDate != null ? moment(experienceInformation.startDate) : null;
                experienceInformation.endDate = experienceInformation.endDate != null ? moment(experienceInformation.endDate) : null;
            });
        }
        return res;
    }
}
