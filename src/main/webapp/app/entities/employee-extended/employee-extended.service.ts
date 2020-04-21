import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

type EntityResponseType = HttpResponse<IEmployee>;
type EntityArrayResponseType = HttpResponse<IEmployee[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeExtendedService extends EmployeeService {
    public resourceUrl = SERVER_API_URL + 'api/employees';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/employees';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(employee: IEmployee): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(employee);
        return this.http
            .post<IEmployee>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(employee: IEmployee): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(employee);
        return this.http
            .put<IEmployee>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IEmployee>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEmployee[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IEmployee[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(employee: IEmployee): IEmployee {
        const copy: IEmployee = Object.assign({}, employee, {
            birthDate: employee.birthDate != null && employee.birthDate.isValid() ? employee.birthDate.format(DATE_FORMAT) : null,
            joiningDate: employee.joiningDate != null && employee.joiningDate.isValid() ? employee.joiningDate.format(DATE_FORMAT) : null,
            terminationDate:
                employee.terminationDate != null && employee.terminationDate.isValid() ? employee.terminationDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.birthDate = res.body.birthDate != null ? moment(res.body.birthDate) : null;
            res.body.joiningDate = res.body.joiningDate != null ? moment(res.body.joiningDate) : null;
            res.body.terminationDate = res.body.terminationDate != null ? moment(res.body.terminationDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((employee: IEmployee) => {
                employee.birthDate = employee.birthDate != null ? moment(employee.birthDate) : null;
                employee.joiningDate = employee.joiningDate != null ? moment(employee.joiningDate) : null;
                employee.terminationDate = employee.terminationDate != null ? moment(employee.terminationDate) : null;
            });
        }
        return res;
    }
}
