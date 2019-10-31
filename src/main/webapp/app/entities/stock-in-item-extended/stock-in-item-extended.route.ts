import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IStockInItem, StockInItem } from 'app/shared/model/stock-in-item.model';
import { StockInItemExtendedService } from 'app/entities/stock-in-item-extended/stock-in-item-extended.service';
import { StockInItemExtendedComponent } from 'app/entities/stock-in-item-extended/stock-in-item-extended.component';
import { StockInItemDetailExtendedComponent } from 'app/entities/stock-in-item-extended/stock-in-item-detail-extended.component';
import { StockInItemUpdateExtendedComponent } from 'app/entities/stock-in-item-extended/stock-in-item-update-extended.component';
import { StockInItemDeletePopupExtendedComponent } from 'app/entities/stock-in-item-extended/stock-in-item-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class StockInItemExtendedResolve implements Resolve<IStockInItem> {
    constructor(private service: StockInItemExtendedService) {}

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

export const stockInItemExtendedRoute: Routes = [
    {
        path: '',
        component: StockInItemExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockInItemDetailExtendedComponent,
        resolve: {
            stockInItem: StockInItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockInItemUpdateExtendedComponent,
        resolve: {
            stockInItem: StockInItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockInItemUpdateExtendedComponent,
        resolve: {
            stockInItem: StockInItemExtendedResolve
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
        component: StockInItemDeletePopupExtendedComponent,
        resolve: {
            stockInItem: StockInItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
