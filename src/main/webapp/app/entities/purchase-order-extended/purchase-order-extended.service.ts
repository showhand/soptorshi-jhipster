import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { bloomAdd } from '@angular/core/src/render3/di';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<IPurchaseOrder>;
type EntityArrayResponseType = HttpResponse<IPurchaseOrder[]>;

@Injectable({ providedIn: 'root' })
export class PurchaseOrderExtendedService extends PurchaseOrderService {
    public reportResourceUrl = SERVER_API_URL + 'api/purchase-order-report';
    public resourceUrlExtended = SERVER_API_URL + 'api/extended/purchase-orders';

    constructor(protected http: HttpClient) {
        super(http);
    }

    create(purchaseOrder: IPurchaseOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseOrder);
        return this.http
            .post<IPurchaseOrder>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(purchaseOrder: IPurchaseOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(purchaseOrder);
        return this.http
            .put<IPurchaseOrder>(this.resourceUrlExtended, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPurchaseOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    downloadPurchaseOrderReport(purchaseOrderId: number): any {
        return this.http.get(`${this.reportResourceUrl}/${purchaseOrderId}`, { responseType: 'blob' }).subscribe((data: any) => {
            SoptorshiUtil.writeFileContent(data, 'application/pdf', 'PurchaseOrderReport');
        });
    }
}
