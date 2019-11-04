import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialPackaging } from 'app/shared/model/commercial-packaging.model';
import { CommercialPackagingService } from 'app/entities/commercial-packaging';

type EntityResponseType = HttpResponse<ICommercialPackaging>;
type EntityArrayResponseType = HttpResponse<ICommercialPackaging[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPackagingExtendedService extends CommercialPackagingService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-packagings';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-packagings';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(commercialPackaging: ICommercialPackaging): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPackaging);
        return this.http
            .post<ICommercialPackaging>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialPackaging: ICommercialPackaging): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPackaging);
        return this.http
            .put<ICommercialPackaging>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialPackaging>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPackaging[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPackaging[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialPackaging: ICommercialPackaging): ICommercialPackaging {
        const copy: ICommercialPackaging = Object.assign({}, commercialPackaging, {
            consignmentDate:
                commercialPackaging.consignmentDate != null && commercialPackaging.consignmentDate.isValid()
                    ? commercialPackaging.consignmentDate.format(DATE_FORMAT)
                    : null,
            createOn:
                commercialPackaging.createOn != null && commercialPackaging.createOn.isValid()
                    ? commercialPackaging.createOn.format(DATE_FORMAT)
                    : null,
            updatedOn:
                commercialPackaging.updatedOn != null && commercialPackaging.updatedOn.isValid()
                    ? commercialPackaging.updatedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.consignmentDate = res.body.consignmentDate != null ? moment(res.body.consignmentDate) : null;
            res.body.createOn = res.body.createOn != null ? moment(res.body.createOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialPackaging: ICommercialPackaging) => {
                commercialPackaging.consignmentDate =
                    commercialPackaging.consignmentDate != null ? moment(commercialPackaging.consignmentDate) : null;
                commercialPackaging.createOn = commercialPackaging.createOn != null ? moment(commercialPackaging.createOn) : null;
                commercialPackaging.updatedOn = commercialPackaging.updatedOn != null ? moment(commercialPackaging.updatedOn) : null;
            });
        }
        return res;
    }
}
