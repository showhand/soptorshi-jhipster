import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockInItem } from 'app/shared/model/stock-in-item.model';
import { IStockInItem } from 'app/shared/model/stock-in-item.model';
import { StockInItemDeletePopupComponentExtended } from 'app/entities/stock-in-item-extended/stock-in-item-delete-dialog.component.extended';
import { StockInItemUpdateComponentExtended } from 'app/entities/stock-in-item-extended/stock-in-item-update.component.extended';
import { StockInItemDetailComponentExtended } from 'app/entities/stock-in-item-extended/stock-in-item-detail.component.extended';
import { StockInItemComponentExtended } from 'app/entities/stock-in-item-extended/stock-in-item.component.extended';
import { StockInItemServiceExtended } from 'app/entities/stock-in-item-extended/stock-in-item.service.extended';

@Injectable({ providedIn: 'root' })
export class StockInItemResolveExtended implements Resolve<IStockInItem> {
    constructor(private service: StockInItemServiceExtended) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockInItem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StockInItem>) => response.ok),
                map((stockInItem: HttpResponse<StockInItem>) => stockInItem.body)
            );
        }
        return of(new StockInItem());
    }
}

export const stockInItemRouteExtended: Routes = [
    {
        path: '',
        component: StockInItemComponentExtended,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockInItemDetailComponentExtended,
        resolve: {
            stockInItem: StockInItemResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockInItemUpdateComponentExtended,
        resolve: {
            stockInItem: StockInItemResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockInItemUpdateComponentExtended,
        resolve: {
            stockInItem: StockInItemResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockInItemPopupRouteExtended: Routes = [
    {
        path: ':id/delete',
        component: StockInItemDeletePopupComponentExtended,
        resolve: {
            stockInItem: StockInItemResolveExtended
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
