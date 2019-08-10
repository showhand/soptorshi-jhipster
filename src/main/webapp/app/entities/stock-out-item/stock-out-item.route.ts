import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockOutItem } from 'app/shared/model/stock-out-item.model';
import { StockOutItemService } from './stock-out-item.service';
import { StockOutItemComponent } from './stock-out-item.component';
import { StockOutItemDetailComponent } from './stock-out-item-detail.component';
import { StockOutItemUpdateComponent } from './stock-out-item-update.component';
import { StockOutItemDeletePopupComponent } from './stock-out-item-delete-dialog.component';
import { IStockOutItem } from 'app/shared/model/stock-out-item.model';

@Injectable({ providedIn: 'root' })
export class StockOutItemResolve implements Resolve<IStockOutItem> {
    constructor(private service: StockOutItemService) {}

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

export const stockOutItemRoute: Routes = [
    {
        path: '',
        component: StockOutItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockOutItemDetailComponent,
        resolve: {
            stockOutItem: StockOutItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockOutItemUpdateComponent,
        resolve: {
            stockOutItem: StockOutItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockOutItemUpdateComponent,
        resolve: {
            stockOutItem: StockOutItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockOutItemPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StockOutItemDeletePopupComponent,
        resolve: {
            stockOutItem: StockOutItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
