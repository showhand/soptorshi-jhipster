import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';
import { CommercialPackagingDetailsService } from 'app/entities/commercial-packaging-details';

type EntityResponseType = HttpResponse<ICommercialPackagingDetails>;
type EntityArrayResponseType = HttpResponse<ICommercialPackagingDetails[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPackagingDetailsExtendedService extends CommercialPackagingDetailsService {
    public resourceUrl = SERVER_API_URL + 'api/commercial-packaging-details';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-packaging-details';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(commercialPackagingDetails: ICommercialPackagingDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPackagingDetails);
        return this.http
            .post<ICommercialPackagingDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialPackagingDetails: ICommercialPackagingDetails): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPackagingDetails);
        return this.http
            .put<ICommercialPackagingDetails>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialPackagingDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPackagingDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPackagingDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialPackagingDetails: ICommercialPackagingDetails): ICommercialPackagingDetails {
        const copy: ICommercialPackagingDetails = Object.assign({}, commercialPackagingDetails, {
            proDate:
                commercialPackagingDetails.proDate != null && commercialPackagingDetails.proDate.isValid()
                    ? commercialPackagingDetails.proDate.format(DATE_FORMAT)
                    : null,
            expDate:
                commercialPackagingDetails.expDate != null && commercialPackagingDetails.expDate.isValid()
                    ? commercialPackagingDetails.expDate.format(DATE_FORMAT)
                    : null,
            createOn:
                commercialPackagingDetails.createOn != null && commercialPackagingDetails.createOn.isValid()
                    ? commercialPackagingDetails.createOn.format(DATE_FORMAT)
                    : null,
            updatedOn:
                commercialPackagingDetails.updatedOn != null && commercialPackagingDetails.updatedOn.isValid()
                    ? commercialPackagingDetails.updatedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.proDate = res.body.proDate != null ? moment(res.body.proDate) : null;
            res.body.expDate = res.body.expDate != null ? moment(res.body.expDate) : null;
            res.body.createOn = res.body.createOn != null ? moment(res.body.createOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialPackagingDetails: ICommercialPackagingDetails) => {
                commercialPackagingDetails.proDate =
                    commercialPackagingDetails.proDate != null ? moment(commercialPackagingDetails.proDate) : null;
                commercialPackagingDetails.expDate =
                    commercialPackagingDetails.expDate != null ? moment(commercialPackagingDetails.expDate) : null;
                commercialPackagingDetails.createOn =
                    commercialPackagingDetails.createOn != null ? moment(commercialPackagingDetails.createOn) : null;
                commercialPackagingDetails.updatedOn =
                    commercialPackagingDetails.updatedOn != null ? moment(commercialPackagingDetails.updatedOn) : null;
            });
        }
        return res;
    }
}
