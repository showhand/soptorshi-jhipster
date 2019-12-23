import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';
import { CommercialPurchaseOrderItemService } from './commercial-purchase-order-item.service';
import { CommercialPurchaseOrderItemComponent } from './commercial-purchase-order-item.component';
import { CommercialPurchaseOrderItemDetailComponent } from './commercial-purchase-order-item-detail.component';
import { CommercialPurchaseOrderItemUpdateComponent } from './commercial-purchase-order-item-update.component';
import { CommercialPurchaseOrderItemDeletePopupComponent } from './commercial-purchase-order-item-delete-dialog.component';
import { ICommercialPurchaseOrderItem } from 'app/shared/model/commercial-purchase-order-item.model';

@Injectable({ providedIn: 'root' })
export class CommercialPurchaseOrderItemResolve implements Resolve<ICommercialPurchaseOrderItem> {
    constructor(private service: CommercialPurchaseOrderItemService) {}

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

export const commercialPurchaseOrderItemRoute: Routes = [
    {
        path: '',
        component: CommercialPurchaseOrderItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrderItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPurchaseOrderItemDetailComponent,
        resolve: {
            commercialPurchaseOrderItem: CommercialPurchaseOrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrderItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPurchaseOrderItemUpdateComponent,
        resolve: {
            commercialPurchaseOrderItem: CommercialPurchaseOrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrderItems'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPurchaseOrderItemUpdateComponent,
        resolve: {
            commercialPurchaseOrderItem: CommercialPurchaseOrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrderItems'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPurchaseOrderItemPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPurchaseOrderItemDeletePopupComponent,
        resolve: {
            commercialPurchaseOrderItem: CommercialPurchaseOrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrderItems'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
