import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChequeRegister } from 'app/shared/model/cheque-register.model';

type EntityResponseType = HttpResponse<IChequeRegister>;
type EntityArrayResponseType = HttpResponse<IChequeRegister[]>;

@Injectable({ providedIn: 'root' })
export class ChequeRegisterService {
    public resourceUrl = SERVER_API_URL + 'api/cheque-registers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/cheque-registers';

    constructor(protected http: HttpClient) {}

    create(chequeRegister: IChequeRegister): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(chequeRegister);
        return this.http
            .post<IChequeRegister>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(chequeRegister: IChequeRegister): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(chequeRegister);
        return this.http
            .put<IChequeRegister>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IChequeRegister>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IChequeRegister[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IChequeRegister[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(chequeRegister: IChequeRegister): IChequeRegister {
        const copy: IChequeRegister = Object.assign({}, chequeRegister, {
            chequeDate:
                chequeRegister.chequeDate != null && chequeRegister.chequeDate.isValid()
                    ? chequeRegister.chequeDate.format(DATE_FORMAT)
                    : null,
            realizationDate:
                chequeRegister.realizationDate != null && chequeRegister.realizationDate.isValid()
                    ? chequeRegister.realizationDate.format(DATE_FORMAT)
                    : null,
            modifiedOn:
                chequeRegister.modifiedOn != null && chequeRegister.modifiedOn.isValid()
                    ? chequeRegister.modifiedOn.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.chequeDate = res.body.chequeDate != null ? moment(res.body.chequeDate) : null;
            res.body.realizationDate = res.body.realizationDate != null ? moment(res.body.realizationDate) : null;
            res.body.modifiedOn = res.body.modifiedOn != null ? moment(res.body.modifiedOn) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((chequeRegister: IChequeRegister) => {
                chequeRegister.chequeDate = chequeRegister.chequeDate != null ? moment(chequeRegister.chequeDate) : null;
                chequeRegister.realizationDate = chequeRegister.realizationDate != null ? moment(chequeRegister.realizationDate) : null;
                chequeRegister.modifiedOn = chequeRegister.modifiedOn != null ? moment(chequeRegister.modifiedOn) : null;
            });
        }
        return res;
    }
}
