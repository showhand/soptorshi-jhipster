import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommercialPoStatus } from 'app/shared/model/commercial-po-status.model';
import { CommercialPoStatusService } from 'app/entities/commercial-po-status';

type EntityResponseType = HttpResponse<ICommercialPoStatus>;
type EntityArrayResponseType = HttpResponse<ICommercialPoStatus[]>;

@Injectable({ providedIn: 'root' })
export class CommercialPoStatusExtendedService extends CommercialPoStatusService {
    public resourceUrl = SERVER_API_URL + 'api/extended/commercial-po-statuses';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commercial-po-statuses';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(commercialPoStatus: ICommercialPoStatus): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPoStatus);
        return this.http
            .post<ICommercialPoStatus>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(commercialPoStatus: ICommercialPoStatus): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commercialPoStatus);
        return this.http
            .put<ICommercialPoStatus>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommercialPoStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPoStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommercialPoStatus[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commercialPoStatus: ICommercialPoStatus): ICommercialPoStatus {
        const copy: ICommercialPoStatus = Object.assign({}, commercialPoStatus, {
            createOn:
                commercialPoStatus.createOn != null && commercialPoStatus.createOn.isValid()
                    ? commercialPoStatus.createOn.format(DATE_FORMAT)
                    : null,
            updatedOn:
                commercialPoStatus.updatedOn != null && commercialPoStatus.updatedOn.isValid()
                    ? commercialPoStatus.updatedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createOn = res.body.createOn != null ? moment(res.body.createOn) : null;
            res.body.updatedOn = res.body.updatedOn != null ? moment(res.body.updatedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commercialPoStatus: ICommercialPoStatus) => {
                commercialPoStatus.createOn = commercialPoStatus.createOn != null ? moment(commercialPoStatus.createOn) : null;
                commercialPoStatus.updatedOn = commercialPoStatus.updatedOn != null ? moment(commercialPoStatus.updatedOn) : null;
            });
        }
        return res;
    }
}
