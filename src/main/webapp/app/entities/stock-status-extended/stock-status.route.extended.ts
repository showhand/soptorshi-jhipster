import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockStatus } from 'app/shared/model/stock-status.model';
import { IStockStatus } from 'app/shared/model/stock-status.model';
import { StockStatusServiceExtended } from 'app/entities/stock-status-extended/stock-status.service.extended';
import { StockStatusDeletePopupComponentExtended } from 'app/entities/stock-status-extended/stock-status-delete-dialog.component.extended';
import { StockStatusUpdateComponentExtended } from 'app/entities/stock-status-extended/stock-status-update.component.extended';
import { StockStatusDetailComponentExtended } from 'app/entities/stock-status-extended/stock-status-detail.component.extended';
import { StockStatusComponentExtended } from 'app/entities/stock-status-extended/stock-status.component.extended';

@Injectable({ providedIn: 'root' })
export class StockStatusResolveExtended implements Resolve<IStockStatus> {
    constructor(private service: StockStatusServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockStatus> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StockStatus>) => response.ok),
                map((stockStatus: HttpResponse<StockStatus>) => stockStatus.body)
            );
        }
        return of(new StockStatus());
    }
}

export const stockStatusRouteExtended: Routes = [
    {
        path: '',
        component: StockStatusComponentExtended,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockStatusDetailComponentExtended,
        resolve: {
            stockStatus: StockStatusResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockStatusUpdateComponentExtended,
        resolve: {
            stockStatus: StockStatusResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockStatusUpdateComponentExtended,
        resolve: {
            stockStatus: StockStatusResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockStatusPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: StockStatusDeletePopupComponentExtended,
        resolve: {
            stockStatus: StockStatusResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
