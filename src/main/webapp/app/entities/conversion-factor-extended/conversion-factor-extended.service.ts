import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConversionFactor } from 'app/shared/model/conversion-factor.model';
import { ConversionFactorService } from 'app/entities/conversion-factor';

type EntityResponseType = HttpResponse<IConversionFactor>;
type EntityArrayResponseType = HttpResponse<IConversionFactor[]>;

@Injectable({ providedIn: 'root' })
export class ConversionFactorExtendedService extends ConversionFactorService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/conversion-factors';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(conversionFactor: IConversionFactor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(conversionFactor);
        return this.http
            .post<IConversionFactor>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(conversionFactor: IConversionFactor): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(conversionFactor);
        return this.http
            .put<IConversionFactor>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
