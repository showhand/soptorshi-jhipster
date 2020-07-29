import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyAreaWiseAccumulation } from 'app/shared/model/supply-area-wise-accumulation.model';
import { SupplyAreaWiseAccumulationService } from 'app/entities/supply-area-wise-accumulation';
import { Observable } from 'rxjs';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<ISupplyAreaWiseAccumulation>;
type EntityArrayResponseType = HttpResponse<ISupplyAreaWiseAccumulation[]>;

@Injectable({ providedIn: 'root' })
export class SupplyAreaWiseAccumulationExtendedService extends SupplyAreaWiseAccumulationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-area-wise-accumulations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-area-wise-accumulations';

    constructor(protected http: HttpClient) {
        super(http);
    }

    bulkPost(supplyAreaWiseAccumulation: ISupplyAreaWiseAccumulation[]): Observable<HttpResponse<ISupplyAreaWiseAccumulation[]>> {
        let copy: any[] = [];
        for (let i = 0; i < supplyAreaWiseAccumulation.length; i++) {
            copy[i] = this.convertDateFromClient(supplyAreaWiseAccumulation[i]);
        }
        return this.http.post<ISupplyAreaWiseAccumulation[]>(`${this.resourceUrl}/bulk`, copy, { observe: 'response' });
    }

    bulkUpdate(supplyAreaWiseAccumulation: ISupplyAreaWiseAccumulation[]): Observable<HttpResponse<ISupplyAreaWiseAccumulation[]>> {
        let copy: any[] = [];
        for (let i = 0; i < supplyAreaWiseAccumulation.length; i++) {
            copy[i] = this.convertDateFromClient(supplyAreaWiseAccumulation[i]);
        }
        return this.http.put<ISupplyAreaWiseAccumulation[]>(`${this.resourceUrl}/bulk`, copy, { observe: 'response' });
    }

    downloadReport(from: string, to: string) {
        return this.http
            .get(`api/extended/supply-report/download/from/${from}/to/${to}`, {
                responseType: 'blob'
            })
            .subscribe((data: any) => {
                SoptorshiUtil.writeFileContent(data, 'application/pdf', 'Summary Report');
            });
    }
}
