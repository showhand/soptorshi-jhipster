import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockOutItem } from 'app/shared/model/stock-out-item.model';
import { IStockOutItem } from 'app/shared/model/stock-out-item.model';
import { StockOutItemServiceExtended } from 'app/entities/stock-out-item-extended/stock-out-item.service.extended';
import { StockOutItemDeletePopupComponentExtended } from 'app/entities/stock-out-item-extended/stock-out-item-delete-dialog.component.extended';
import { StockOutItemUpdateComponentExtended } from 'app/entities/stock-out-item-extended/stock-out-item-update.component.extended';
import { StockOutItemDetailComponentExtended } from 'app/entities/stock-out-item-extended/stock-out-item-detail.component.extended';
import { StockOutItemComponentExtended } from 'app/entities/stock-out-item-extended/stock-out-item.component.extended';

@Injectable({ providedIn: 'root' })
export class StockOutItemResolveExtended implements Resolve<IStockOutItem> {
    constructor(private service: StockOutItemServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockOutItem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StockOutItem>) => response.ok),
                map((stockOutItem: HttpResponse<StockOutItem>) => stockOutItem.body)
            );
        }
        return of(new StockOutItem());
    }
}

export const stockOutItemRouteExtended: Routes = [
    {
        path: '',
        component: StockOutItemComponentExtended,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockOutItemDetailComponentExtended,
        resolve: {
            stockOutItem: StockOutItemResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockOutItemUpdateComponentExtended,
        resolve: {
            stockOutItem: StockOutItemResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockOutItemUpdateComponentExtended,
        resolve: {
            stockOutItem: StockOutItemResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockOutItemPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: StockOutItemDeletePopupComponentExtended,
        resolve: {
            stockOutItem: StockOutItemResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
