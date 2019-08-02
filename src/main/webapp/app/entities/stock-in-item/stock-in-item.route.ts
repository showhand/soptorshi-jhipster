import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockInItem } from 'app/shared/model/stock-in-item.model';
import { StockInItemService } from './stock-in-item.service';
import { StockInItemComponent } from './stock-in-item.component';
import { StockInItemDetailComponent } from './stock-in-item-detail.component';
import { StockInItemUpdateComponent } from './stock-in-item-update.component';
import { StockInItemDeletePopupComponent } from './stock-in-item-delete-dialog.component';
import { IStockInItem } from 'app/shared/model/stock-in-item.model';

@Injectable({ providedIn: 'root' })
export class StockInItemResolve implements Resolve<IStockInItem> {
    constructor(private service: StockInItemService) {}

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

export const stockInItemRoute: Routes = [
    {
        path: '',
        component: StockInItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockInItemDetailComponent,
        resolve: {
            stockInItem: StockInItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockInItemUpdateComponent,
        resolve: {
            stockInItem: StockInItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockInItemUpdateComponent,
        resolve: {
            stockInItem: StockInItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockInItemPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StockInItemDeletePopupComponent,
        resolve: {
            stockInItem: StockInItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StockInItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
