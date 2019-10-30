import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IStockOutItem, StockOutItem } from 'app/shared/model/stock-out-item.model';
import { StockOutItemExtendedService } from 'app/entities/stock-out-item-extended/stock-out-item-extended.service';
import { StockOutItemExtendedComponent } from 'app/entities/stock-out-item-extended/stock-out-item-extended.component';
import { StockOutItemDetailExtendedComponent } from 'app/entities/stock-out-item-extended/stock-out-item-detail-extended.component';
import { StockOutItemUpdateExtendedComponent } from 'app/entities/stock-out-item-extended/stock-out-item-update-extended.component';
import { StockOutItemDeletePopupComponentExtended } from 'app/entities/stock-out-item-extended/stock-out-item-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class StockOutItemResolveExtended implements Resolve<IStockOutItem> {
    constructor(private service: StockOutItemExtendedService) {}

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

export const stockOutItemExtendedRoute: Routes = [
    {
        path: '',
        component: StockOutItemExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockOutItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockOutItemDetailExtendedComponent,
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
        component: StockOutItemUpdateExtendedComponent,
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
        component: StockOutItemUpdateExtendedComponent,
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
