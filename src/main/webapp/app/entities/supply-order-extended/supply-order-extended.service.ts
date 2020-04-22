import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyOrder, SupplyOrderStatus } from 'app/shared/model/supply-order.model';
import { SupplyOrderService } from 'app/entities/supply-order';
import { Observable } from 'rxjs';
import { Moment } from 'moment';
import { map } from 'rxjs/operators';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<ISupplyOrder>;
type EntityArrayResponseType = HttpResponse<ISupplyOrder[]>;

@Injectable({ providedIn: 'root' })
export class SupplyOrderExtendedService extends SupplyOrderService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-orders';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-orders';

    constructor(protected http: HttpClient) {
        super(http);
    }

    updateStatusAndReferenceNo(
        referenceNo: string,
        fromDate: Moment,
        toDate: Moment,
        status: SupplyOrderStatus
    ): Observable<HttpResponse<number>> {
        return this.http.get<number>(
            `${this.resourceUrl}/referenceNo/` + referenceNo + `/fromDate/` + fromDate + `/toDate/` + toDate + `/status/` + status,
            { observe: 'response' }
        );
    }

    getDistinctSupplyOrderDates(): Observable<HttpResponse<Moment[]>> {
        return this.http
            .get<Moment[]>(`${this.resourceUrl}/dates`, { observe: 'response' })
            .pipe(map((res: HttpResponse<Moment[]>) => this.convertDateArrayFromServerResponse(res)));
    }

    protected convertDateArrayFromServerResponse(res: HttpResponse<Moment[]>): HttpResponse<Moment[]> {
        if (res.body) {
            res.body.forEach((m: Moment) => {
                m = m != null ? m : null;
            });
        }
        return res;
    }

    downloadAccumulatedOrders(refNo: String) {
        return this.http
            .get(`${this.resourceUrl}/download/referenceNo/${refNo}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Accumulated Orders');
            });
    }
}
