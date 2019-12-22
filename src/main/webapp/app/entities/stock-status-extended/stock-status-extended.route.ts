import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IStockStatus, StockStatus } from 'app/shared/model/stock-status.model';
import { StockStatusExtendedService } from 'app/entities/stock-status-extended/stock-status-extended.service';
import { StockStatusExtendedComponent } from 'app/entities/stock-status-extended/stock-status-extended.component';
import { StockStatusDetailExtendedComponent } from 'app/entities/stock-status-extended/stock-status-detail-extended.component';
import { StockStatusUpdateExtendedComponent } from 'app/entities/stock-status-extended/stock-status-update-extended.component';
import { StockStatusDeletePopupExtendedComponent } from 'app/entities/stock-status-extended/stock-status-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class StockStatusExtendedResolve implements Resolve<IStockStatus> {
    constructor(private service: StockStatusExtendedService) {}

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

export const stockStatusExtendedRoute: Routes = [
    {
        path: '',
        component: StockStatusExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockStatusDetailExtendedComponent,
        resolve: {
            stockStatus: StockStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockStatusUpdateExtendedComponent,
        resolve: {
            stockStatus: StockStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockStatusUpdateExtendedComponent,
        resolve: {
            stockStatus: StockStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockStatusPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: StockStatusDeletePopupExtendedComponent,
        resolve: {
            stockStatus: StockStatusExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
