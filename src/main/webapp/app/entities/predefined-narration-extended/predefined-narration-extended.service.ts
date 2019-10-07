import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPredefinedNarration } from 'app/shared/model/predefined-narration.model';
import { PredefinedNarrationService } from 'app/entities/predefined-narration';

type EntityResponseType = HttpResponse<IPredefinedNarration>;
type EntityArrayResponseType = HttpResponse<IPredefinedNarration[]>;

@Injectable({ providedIn: 'root' })
export class PredefinedNarrationExtendedService extends PredefinedNarrationService {
    public resourceExtendedUrl = SERVER_API_URL + 'api/extended/predefined-narrations';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(predefinedNarration: IPredefinedNarration): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(predefinedNarration);
        return this.http
            .post<IPredefinedNarration>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(predefinedNarration: IPredefinedNarration): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(predefinedNarration);
        return this.http
            .put<IPredefinedNarration>(this.resourceExtendedUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
}
