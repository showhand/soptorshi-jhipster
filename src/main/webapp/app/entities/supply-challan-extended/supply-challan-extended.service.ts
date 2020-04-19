import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyChallan } from 'app/shared/model/supply-challan.model';
import { SupplyChallanService } from 'app/entities/supply-challan/supply-challan.service';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<ISupplyChallan>;
type EntityArrayResponseType = HttpResponse<ISupplyChallan[]>;

@Injectable({ providedIn: 'root' })
export class SupplyChallanExtendedService extends SupplyChallanService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-challans';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-challans';

    constructor(protected http: HttpClient) {
        super(http);
    }

    downloadChallan(supplyChallan: ISupplyChallan) {
        return this.http
            .get(`${this.resourceUrl}/download/${supplyChallan.id}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Challan');
            });
    }
}
