import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISupplyShop, SupplyShop } from 'app/shared/model/supply-shop.model';
import { SupplyShopService } from './supply-shop.service';
import { SupplyShopComponent } from './supply-shop.component';
import { SupplyShopDetailComponent } from './supply-shop-detail.component';
import { SupplyShopUpdateComponent } from './supply-shop-update.component';
import { SupplyShopDeletePopupComponent } from './supply-shop-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class SupplyShopResolve implements Resolve<ISupplyShop> {
    constructor(private service: SupplyShopService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplyShop> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SupplyShop>) => response.ok),
                map((supplyShop: HttpResponse<SupplyShop>) => supplyShop.body)
            );
        }
        return of(new SupplyShop());
    }
}

export const supplyShopRoute: Routes = [
    {
        path: '',
        component: SupplyShopComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyShops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SupplyShopDetailComponent,
        resolve: {
            supplyShop: SupplyShopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyShops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SupplyShopUpdateComponent,
        resolve: {
            supplyShop: SupplyShopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyShops'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SupplyShopUpdateComponent,
        resolve: {
            supplyShop: SupplyShopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyShops'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const supplyShopPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SupplyShopDeletePopupComponent,
        resolve: {
            supplyShop: SupplyShopResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SupplyShops'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
