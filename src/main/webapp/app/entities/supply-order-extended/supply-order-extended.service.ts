import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyOrder } from 'app/shared/model/supply-order.model';
import { SupplyOrderService } from 'app/entities/supply-order';
import { Observable } from 'rxjs';
import { Moment } from 'moment';
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

    bulkUpdate(supplyOrder: ISupplyOrder[]): Observable<HttpResponse<ISupplyOrder[]>> {
        let copy: any[] = [];
        for (let i = 0; i < supplyOrder.length; i++) {
            copy[i] = this.convertDateFromClient(supplyOrder[i]);
        }
        return this.http.put<ISupplyOrder[]>(`${this.resourceUrl}/bulk`, copy, { observe: 'response' });
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
