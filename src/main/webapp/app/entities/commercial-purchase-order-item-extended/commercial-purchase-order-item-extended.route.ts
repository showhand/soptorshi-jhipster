import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPurchaseOrderItem, ICommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';
import { CommercialPurchaseOrderItemExtendedService } from './commercial-purchase-order-item-extended.service';
import { CommercialPurchaseOrderItemExtendedComponent } from './commercial-purchase-order-item-extended.component';
import { CommercialPurchaseOrderItemDetailExtendedComponent } from './commercial-purchase-order-item-detail-extended.component';
import { CommercialPurchaseOrderItemUpdateExtendedComponent } from './commercial-purchase-order-item-update-extended.component';
import { CommercialPurchaseOrderItemDeletePopupExtendedComponent } from './commercial-purchase-order-item-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialPurchaseOrderItemExtendedResolve implements Resolve<ICommercialPurchaseOrderItem> {
    constructor(private service: CommercialPurchaseOrderItemExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialPurchaseOrderItem> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialPurchaseOrderItem>) => response.ok),
                map((commercialPurchaseOrderItem: HttpResponse<CommercialPurchaseOrderItem>) => commercialPurchaseOrderItem.body)
            );
        }
        return of(new CommercialPurchaseOrderItem());
    }
}

export const commercialPurchaseOrderItemExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialPurchaseOrderItemExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrderItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPurchaseOrderItemDetailExtendedComponent,
        resolve: {
            commercialPurchaseOrderItem: CommercialPurchaseOrderItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrderItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPurchaseOrderItemUpdateExtendedComponent,
        resolve: {
            commercialPurchaseOrderItem: CommercialPurchaseOrderItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrderItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPurchaseOrderItemUpdateExtendedComponent,
        resolve: {
            commercialPurchaseOrderItem: CommercialPurchaseOrderItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrderItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPurchaseOrderItemPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPurchaseOrderItemDeletePopupExtendedComponent,
        resolve: {
            commercialPurchaseOrderItem: CommercialPurchaseOrderItemExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrderItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
