import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { SERVER_API_URL } from 'app/app.constants';
import { ISupplyZoneWiseAccumulation } from 'app/shared/model/supply-zone-wise-accumulation.model';
import { SupplyZoneWiseAccumulationService } from 'app/entities/supply-zone-wise-accumulation';
import { Observable } from 'rxjs';
import { SoptorshiUtil } from 'app/shared/util/SoptorshiUtil';

type EntityResponseType = HttpResponse<ISupplyZoneWiseAccumulation>;
type EntityArrayResponseType = HttpResponse<ISupplyZoneWiseAccumulation[]>;

@Injectable({ providedIn: 'root' })
export class SupplyZoneWiseAccumulationExtendedService extends SupplyZoneWiseAccumulationService {
    public resourceUrl = SERVER_API_URL + 'api/extended/supply-zone-wise-accumulations';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/supply-zone-wise-accumulations';

    constructor(protected http: HttpClient) {
        super(http);
    }

    bulkPost(supplyZoneWiseAccumulation: ISupplyZoneWiseAccumulation[]): Observable<HttpResponse<ISupplyZoneWiseAccumulation[]>> {
        let copy: any[] = [];
        for (let i = 0; i < supplyZoneWiseAccumulation.length; i++) {
            copy[i] = this.convertDateFromClient(supplyZoneWiseAccumulation[i]);
        }
        return this.http.post<ISupplyZoneWiseAccumulation[]>(`${this.resourceUrl}/bulk`, copy, { observe: 'response' });
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
