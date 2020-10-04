import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { IStockOutItem } from 'app/shared/model/stock-out-item.model';
import { StockOutItemService } from 'app/entities/stock-out-item';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<IStockOutItem>;
type EntityArrayResponseType = HttpResponse<IStockOutItem[]>;

@Injectable({ providedIn: 'root' })
export class StockOutItemExtendedService extends StockOutItemService {
    public resourceUrl = SERVER_API_URL + 'api/extended/stock-out-items';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/stock-out-items';

    constructor(protected http: HttpClient) {
        super(http);
    }

    generateChallan(id: number) {
        return this.http
            .get(`${this.resourceUrl}/challan/${id}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', `Challan ${id}`);
            });
    }
}
